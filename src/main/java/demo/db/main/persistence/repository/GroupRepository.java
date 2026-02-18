package demo.db.main.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import demo.db.main.persistence.domain.GroupDAO;

public interface GroupRepository extends JpaRepository<GroupDAO, Integer> {
	
	@Query("SELECT g FROM GroupDAO g")
	public List<GroupDAO> findAllGroups();
	
	public GroupDAO findByGroupId(String groupId);

	
}
