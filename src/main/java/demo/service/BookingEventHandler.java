package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.db.main.persistence.domain.BookingDAO;
import demo.db.main.persistence.repository.BookingRepository;


public class BookingEventHandler implements BookingService {
	
	@Autowired
	private  BookingRepository bookingRepository ;

	@Override
	public List<BookingDAO> getUserBookings(String userId) {
		return bookingRepository.findByUserId(userId); 
	}

	@Override
	public List<BookingDAO> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

}
