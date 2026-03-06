package demo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.BookingDAO;
import demo.db.main.persistence.repository.BookingRepository;

public class BookingEventHandler implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public List<CommonJson> getAllUserFutureBookings() {
		List<Object[]> results = bookingRepository.findAllUserFutureBookings();
		
		List<CommonJson> bookingList = new ArrayList<>();

		for (Object[] row : results) {
		    CommonJson json = new CommonJson();
		    json.set("bookingId", row[0]);
		    json.set("title", row[1]);
		    json.set("status", row[2]);
		    json.set("startAt", row[3]);
		    json.set("endAt", row[4]);
		    json.set("reviewedBy", row[5]);
		    json.set("roomName", row[6]);
		    json.set("userId", row[7]);
		    json.set("userName", row[8]);
		    bookingList.add(json);
		}
		
		return bookingList;
	}

	@Override
	public List<CommonJson> getMyBookings(String userId) {

		List<Object[]> results = bookingRepository.findBookingByUserId(userId);
		List<CommonJson> bookingList = new ArrayList<>();

		for (Object[] row : results) {
			CommonJson json = new CommonJson();
			json.set("bookingId", row[0]);
			json.set("title", row[1]);
			json.set("status", row[2]);
			json.set("startAt", row[3]);
			json.set("endAt", row[4]);
			json.set("roomName", row[5]);
			bookingList.add(json);
		}
		return bookingList;
	}

//	public List<CommonJson> getAllPendingAprovalBooking() {
//		List<Object[]> results = bookingRepository.findAllPendingFutureBookings();
//
//		List<CommonJson> bookingList = new ArrayList<>();
//
//		for (Object[] row : results) {
//			CommonJson json = new CommonJson();
//			json.set("bookingId", row[0]);
//			json.set("title", row[1]);
//			json.set("userId", row[2]);
//			json.set("status", row[3]);
//			json.set("roomName", row[4]);
//			json.set("startAt", row[5]);
//			json.set("endAt", row[6]);
//			bookingList.add(json);
//		}
//
//		return bookingList;
//
//	}

	public BookingDAO cancelBooking(String bookingId) throws Exception {
		BookingDAO booking = bookingRepository.findBybookingId(bookingId);

		if (booking == null) {
			throw new Exception("Booking not found");
		}

		booking.setStatus("CANCELLED");
		booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		bookingRepository.save(booking);

		return booking;
	}
	
	public BookingDAO approveBooking(String bookingId) throws Exception {
		BookingDAO booking = bookingRepository.findBybookingId(bookingId);

		if (booking == null) {
			throw new Exception("Booking not found");
		}

		booking.setStatus("APPROVED");
		booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		bookingRepository.save(booking);

		return booking;
	}

	public BookingDAO rejectBooking(String bookingId) throws Exception {
		BookingDAO booking = bookingRepository.findBybookingId(bookingId);

		if (booking == null) {
			throw new Exception("Booking not found");
		}

		booking.setStatus("REJECTED");
		booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		bookingRepository.save(booking);

		return booking;
	}
}
