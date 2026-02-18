package demo.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.BookingDAO;
import demo.db.main.persistence.domain.RoomDAO;
import demo.db.main.persistence.domain.TimeslotDAO;
import demo.db.main.persistence.repository.BookingRepository;
import demo.db.main.persistence.repository.RoomRepository;
import demo.db.main.persistence.repository.TimeslotRepository;

public class RoomEventHandler implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private TimeslotRepository timeslotRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public CommonJson addRoom(String name, String description, String capacity, String status) throws Exception {
		CommonJson result = new CommonJson();
		RoomDAO room = new RoomDAO();

		room.setRoomId(UUID.randomUUID().toString());
		room.setName(name);
		room.setDescription(description);
		room.setCapacity(capacity);
		room.setStatus(status);
		room.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		room.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		roomRepository.save(room);

		result.set("success", Boolean.TRUE);

		return result;
	}

	// book one room with one or more timeslot(s).
	@Transactional
	public CommonJson bookRoom(List<CommonJson> inputJsonList) throws Exception {
		String roomId = inputJsonList.get(0).get("roomId");

		RoomDAO room = roomRepository.findByRoomId(roomId);

		// checkroom exist
		if (room == null) {
			throw new Exception("Room not found");
		}

		for (CommonJson inputJson : inputJsonList) {
			String userId = inputJson.get("userId");
			Timestamp startAt = Timestamp.valueOf(inputJson.get("start_at"));
			Timestamp endAt = Timestamp.valueOf(inputJson.get("end_at"));

			// check endAt > startAt
			if (startAt.after(endAt)) {
				throw new Exception("start_at must be before end_at");
			}

			// check if enuf capacity
			int currentBookings = bookingRepository.countOverlappingBookings(roomId, startAt, endAt);
			
			if(currentBookings >= Integer.parseInt(room.getCapacity())) {
				throw new Exception("Room full for " + startAt + " - " + endAt);
			}

			//save booking
			BookingDAO booking = new BookingDAO();
			booking.setUserId(userId);
			booking.setRoomId(roomId);
			booking.setStatus("PENDING");
			booking.setStartAt(startAt);
			booking.setStartAt(endAt);

			bookingRepository.save(booking);

		}

		return new CommonJson().set("success", Boolean.TRUE);

	}

	public RoomDAO getRoomById(String roomId) {
		return roomRepository.findByRoomId(roomId);
	}

	@Override
	public List<RoomDAO> getRooms() {
		return roomRepository.findAll();
	}

	@Override
	public List<RoomDAO> findUserAvailableRooms(String userId) {

		return roomRepository.findUserAvailableRooms(userId);
	}

	// if endTime is 00:00 treat it as 24:00 (midnight) in db to avoid infinite loop
	public List<CommonJson> getRoomAvailableTimeSlot(String roomId, String date) {
		List<CommonJson> result = new ArrayList<>();

		LocalDate localDate = LocalDate.parse(date);
		String dayType = localDate.getDayOfWeek().name();

		TimeslotDAO timeslot = timeslotRepository.findByRoomIdAndDayType(roomId, dayType);

		// timeslot null , not allow booking on that day
		if (timeslot == null)
			return result;

		RoomDAO room = roomRepository.findByRoomId(roomId);

		LocalTime startTime = timeslot.getStartTime();
		LocalTime endTime = timeslot.getEndTime();
		LocalTime slotEnd = timeslot.getEndTime();

		while (startTime.isBefore(endTime)) {
			slotEnd = startTime.plusMinutes(timeslot.getIntervalMinutes());

			int bookingCount = bookingRepository.countByRoomIdAndStartAt(roomId,
					Timestamp.valueOf(localDate.atTime(startTime)));

			int capacity = Integer.parseInt(room.getCapacity());

			CommonJson slot = new CommonJson();
			slot.set("date", date);
			slot.set("slotStart", startTime.toString());
			slot.set("slotEnd", slotEnd.toString());
			slot.set("available", bookingCount < capacity);

			result.add(slot);
			startTime = slotEnd;
		}

		return result;
	}

}
