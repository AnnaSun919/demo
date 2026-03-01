package demo.service;

import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.BookingDAO;
import java.util.List;

public interface BookingService {

	public List<CommonJson>  getMyBookings(String userId);
	
	public List<BookingDAO> getAllBookings();
	
	public BookingDAO cancelBooking(String bookingId) throws Exception;

}
