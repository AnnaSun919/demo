package demo.db.main.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import demo.db.main.persistence.domain.TimeslotDAO;


public interface TimeslotRepository extends JpaRepository<TimeslotDAO, Integer> {
	
	public TimeslotDAO findByRoomIdAndDayType(String roomId, String dayType);

	public void deleteByRoomId(String roomId);

	public List<TimeslotDAO> findByRoomId(String roomId);
	
}
