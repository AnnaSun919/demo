package demo.service;

import demo.db.main.persistence.domain.BookingDAO;

public interface BookingService {

	public <List> BookingDAO getBookings(String userId);

}
