package demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.db.main.persistence.domain.BookingDAO;
import demo.db.main.persistence.repository.BookingRepository;


public class BookingEventHandler implements BookingService {
	
	@Autowired
	private  BookingRepository bookingRepository ;

	@Override
	public <List>BookingDAO getBookings(String userId) {
		return bookingRepository.findByUserId(userId); 
	}

}
