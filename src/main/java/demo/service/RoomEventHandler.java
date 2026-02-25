package demo.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.BookingDAO;
import demo.db.main.persistence.domain.RoomDAO;
import demo.db.main.persistence.domain.RoomGroupEligibilityDAO;
import demo.db.main.persistence.domain.TimeslotDAO;
import demo.db.main.persistence.repository.BookingRepository;
import demo.db.main.persistence.repository.RoomGroupEligibilityRepository;
import demo.db.main.persistence.repository.RoomRepository;
import demo.db.main.persistence.repository.TimeslotRepository;

public class RoomEventHandler implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private TimeslotRepository timeslotRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RoomGroupEligibilityRepository roomGroupEligibilityRepository;

	@Transactional
	public CommonJson addRoom(String name, String description, String capacity, String status, String isPublic,
			String intervalMins, JSONArray groupIds, JSONArray timeslots) throws Exception {
		CommonJson result = new CommonJson();
		RoomDAO room = new RoomDAO();

		// check if room name unique
		if (roomRepository.findByName(name) != null) {
			throw new Exception("Room name is already taken ");
		}

		room.setRoomId(UUID.randomUUID().toString());
		room.setName(name);
		room.setDescription(description);
		room.setCapacity(capacity);
		room.setStatus(status);
		room.setIsPublic(isPublic);
		room.setIntervalMinutes(Integer.parseInt(intervalMins));
		room.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		room.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		// add group eligiblity
		if (groupIds != null && groupIds.length() > 0) {
			for (int i = 0; i < groupIds.length(); i++) {
				RoomGroupEligibilityDAO eligibility = new RoomGroupEligibilityDAO();
				eligibility.setRoomId(room.getRoomId());
				eligibility.setGroupId(String.valueOf(groupIds.getInt(i)));
				eligibility.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				eligibility.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
				roomGroupEligibilityRepository.save(eligibility);
			}
		}

		// add available timelsot
		if (timeslots != null && timeslots.length() > 0) {
			for (int i = 0; i < timeslots.length(); i++) {
				JSONObject ts = timeslots.getJSONObject(i);
				TimeslotDAO timeslot = new TimeslotDAO();
				timeslot.setRoomId(room.getRoomId());
				timeslot.setDayType(ts.getString("dayType"));
				timeslot.setStartTime(LocalTime.parse(ts.getString("startTime")));
				timeslot.setEndTime(LocalTime.parse(ts.getString("endTime")));
				timeslotRepository.save(timeslot);
			}
		}

		roomRepository.save(room);

		result.set("room", room);
		return result;
	}

	// book one room with one or more timeslot(s).
	@Transactional
	public CommonJson bookRoom(String userId, String roomId, JSONArray timeslots) throws Exception {

		RoomDAO room = roomRepository.findByRoomId(roomId);

		// checkroom exist
		if (room == null) {
			throw new Exception("Room not found");
		}

		for (int i = 0; i < timeslots.length(); i++) {
			JSONObject slot = timeslots.getJSONObject(i);

			Timestamp startAt = Timestamp.valueOf(slot.getString("start_at"));
			Timestamp endAt = Timestamp.valueOf(slot.getString("end_at"));

			// check endAt > startAt
			if (startAt.after(endAt)) {
				throw new Exception("start_at must be before end_at");
			}

			// check if enuf capacity
			int currentBookings = bookingRepository.countOverlappingBookings(roomId, startAt, endAt);

			if (currentBookings >= Integer.parseInt(room.getCapacity())) {
				throw new Exception("Room full for " + startAt + " - " + endAt);
			}

			// avoid same user duplicate timebooking
			if (bookingRepository.findUserOverlappingBooking(userId, roomId, startAt, endAt) != null) {
				throw new Exception("You already booked this room for this timeslot");
			}

			// save booking
			BookingDAO booking = new BookingDAO();
			booking.setUserId(userId);
			booking.setRoomId(roomId);
			booking.setStatus("PENDING");
			booking.setStartAt(startAt);
			booking.setEndAt(endAt);
			booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

			bookingRepository.save(booking);

		}

		return new CommonJson().set("success", Boolean.TRUE);
	}

	public CommonJson getRoomById(String roomId) {
	    CommonJson result = new CommonJson();
	    RoomDAO room = roomRepository.findByRoomId(roomId);
	    
	    List<RoomGroupEligibilityDAO> eligibilities = roomGroupEligibilityRepository.findByRoomId(roomId);
	    List<Integer> groupIds = eligibilities.stream()
	        .map(e -> Integer.parseInt(e.getGroupId()))
	        .collect(Collectors.toList());
	    
	    result.set("room", room);
	    result.set("groupIds", groupIds);
	    return result;
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
	public List<CommonJson> getRoomAvailableTimeSlot(String userId, String roomId, String date) {
		List<CommonJson> result = new ArrayList<>();

		LocalDate localDate = LocalDate.parse(date);
		String dayType = localDate.getDayOfWeek().name();

		TimeslotDAO timeslot = timeslotRepository.findByRoomIdAndDayType(roomId, dayType);

		// timeslot null , not allow booking on that day
		if (timeslot == null)
			return result;

		RoomDAO room = roomRepository.findByRoomId(roomId);
		int capacity = Integer.parseInt(room.getCapacity());

		LocalTime startTime = timeslot.getStartTime();
		LocalTime endTime = timeslot.getEndTime();

		while (startTime.isBefore(endTime)) {
			LocalTime slotEnd = startTime.plusMinutes(room.getIntervalMinutes());

			Timestamp startTimestamp = Timestamp.valueOf(localDate.atTime(startTime));
			Timestamp endTimestamp = Timestamp.valueOf(localDate.atTime(slotEnd));

			// check capacity
			int bookingCount = bookingRepository.countByRoomIdAndStartAt(roomId,
					Timestamp.valueOf(localDate.atTime(startTime)));
			// check if user has already booked the timeslot
			boolean userBooked = bookingRepository.findUserOverlappingBooking(userId, roomId, startTimestamp,
					endTimestamp) != null;

			CommonJson slot = new CommonJson();
			slot.set("date", date);
			slot.set("slotStart", startTime.toString());
			slot.set("slotEnd", slotEnd.toString());
			slot.set("available", bookingCount < capacity && !userBooked);

			result.add(slot);
			startTime = slotEnd;
		}

		return result;
	}

	@Transactional
	public CommonJson editRoom(String roomId, String description, String capacity, String status, String isPublic,
			String intervalMins, JSONArray groupIds, JSONArray timeslots) throws Exception {
		CommonJson result = new CommonJson();

		RoomDAO room = roomRepository.findByRoomId(roomId);
		if (room == null) {
			throw new Exception("Room not found");
		}

		room.setDescription(description);
		room.setCapacity(capacity);
		room.setStatus(status);
		room.setIsPublic(isPublic);
		room.setIntervalMinutes(Integer.parseInt(intervalMins));
		room.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		// Remove existing group eligibilities and re-add
		roomGroupEligibilityRepository.deleteByRoomId(roomId);

		List<Integer> listofgroupId = new ArrayList();

		if (groupIds != null && groupIds.length() > 0) {
			for (int i = 0; i < groupIds.length(); i++) {
				RoomGroupEligibilityDAO eligibility = new RoomGroupEligibilityDAO();
				eligibility.setRoomId(room.getRoomId());
				eligibility.setGroupId(String.valueOf(groupIds.getInt(i)));
				eligibility.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				eligibility.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
				roomGroupEligibilityRepository.save(eligibility);
				listofgroupId.add(groupIds.getInt(i));
			}
		}

		timeslotRepository.deleteByRoomId(roomId);

		if (timeslots != null && timeslots.length() > 0) {
			for (int i = 0; i < timeslots.length(); i++) {
				JSONObject ts = timeslots.getJSONObject(i);
				TimeslotDAO timeslot = new TimeslotDAO();
				timeslot.setRoomId(room.getRoomId());
				timeslot.setDayType(ts.getString("dayType"));
				timeslot.setStartTime(LocalTime.parse(ts.getString("startTime")));
				timeslot.setEndTime(LocalTime.parse(ts.getString("endTime")));
				timeslotRepository.save(timeslot);
			}
		}

		roomRepository.save(room);

		result.set("description", description);
		result.set("capacity", capacity);
		result.set("status", status);
		result.set("isPublic", isPublic);
		result.set("groupIds", listofgroupId);
		return result;
	}

	@Transactional
	public CommonJson deleteRoom(String roomId) throws Exception {
		CommonJson resultJson = new CommonJson();

		RoomDAO room = roomRepository.findByRoomId(roomId);

		if (room == null) {
			throw new Exception("Room not found");
		}

		// Delete the room
		roomRepository.deleteByRoomId(roomId);
		// also the room_group_eligibility;
		roomGroupEligibilityRepository.deleteByRoomId(roomId);

		return resultJson.set("success", true);
	}

}
