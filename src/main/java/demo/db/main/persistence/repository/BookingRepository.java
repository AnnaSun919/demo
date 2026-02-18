package demo.db.main.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.db.main.persistence.domain.BookingDAO;


public interface BookingRepository extends JpaRepository<BookingDAO, Integer> {
	
	public List<BookingDAO> findByUserId(String userId);

	public int countByRoomIdAndStartAt(String roomId, Timestamp valueOf);
	
}
