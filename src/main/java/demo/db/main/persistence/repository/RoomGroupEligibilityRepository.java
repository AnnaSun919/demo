package demo.db.main.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.db.main.persistence.domain.RoomGroupEligibilityDAO;


public interface RoomGroupEligibilityRepository extends JpaRepository<RoomGroupEligibilityDAO, Integer> {
	
	
}
