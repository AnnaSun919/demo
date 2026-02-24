package demo.db.main.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.db.main.persistence.domain.RoomGroupEligibilityDAO;


public interface RoomGroupEligibilityRepository extends JpaRepository<RoomGroupEligibilityDAO, Integer> {

	public List<RoomGroupEligibilityDAO> findByRoomId(String roomId);
	
	@Transactional
	public void deleteByRoomId(String roomId);
	
	
}
