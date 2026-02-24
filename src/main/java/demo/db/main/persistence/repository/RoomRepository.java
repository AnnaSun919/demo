package demo.db.main.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.db.main.persistence.domain.RoomDAO;

public interface RoomRepository extends JpaRepository<RoomDAO, Integer> {

	public RoomDAO findByRoomId(String roomId);

	public RoomDAO findByName(String name);

	@Query(value = "SELECT DISTINCT r.* FROM ROOM r " + "LEFT JOIN ROOM_GROUP_ELIGIBILITY rge ON r.id = rge.room_id "
			+ "LEFT JOIN USER_GROUP ug ON rge.group_id = ug.group_id "
			+ "WHERE status = 'open' and (r.is_public = 'PUBLIC' " + "OR ug.user_id = :userId)", nativeQuery = true)
	public List<RoomDAO> findUserAvailableRooms(@Param("userId") String userId);

	@Query("SELECT r FROM RoomDAO r")
	public List<RoomDAO> findAllRoom();

	@Transactional
	public void deleteByRoomId(String roomId);

}
