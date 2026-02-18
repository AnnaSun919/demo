package demo.db.main.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import demo.db.main.persistence.domain.RoomDAO;


public interface RoomRepository extends JpaRepository<RoomDAO, Integer> {
	
	@Query("SELECT r FROM RoomDAO r")
	public List<RoomDAO> findAllRoom();
	
	public RoomDAO findByRoomId(String roomId);

	
}
