package demo.service;

import demo.db.main.persistence.domain.BookingDAO;
import java.util.List;

public interface BookingService {

	public List<BookingDAO> getUserBookings(String userId);
	
	public List<BookingDAO> getAllBookings();

}
