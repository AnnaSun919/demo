package demo.db.main.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.db.main.persistence.domain.BookingDAO;

public interface BookingRepository extends JpaRepository<BookingDAO, Integer> {

	public List<BookingDAO> findByUserId(String userId);

	public int countByRoomIdAndStartAt(String roomId, Timestamp valueOf);

	@Query(value = "SELECT COUNT(*) FROM BOOKING " + "WHERE ROOMID = :roomId "
			+ "AND START_AT < :endAt AND END_AT > :startAt "
			+ "AND STATUS IN ('PENDING', 'APPROVED')", nativeQuery = true)
	int countOverlappingBookings(@Param("roomId") String roomId, @Param("startAt") Timestamp startAt,
			@Param("endAt") Timestamp endAt);

	@Query(value = "SELECT * FROM BOOKING " + "WHERE USERID = :userId " + "AND ROOMID = :roomId "
			+ "AND START_AT < :endAt AND END_AT > :startAt "
			+ "AND STATUS IN ('PENDING', 'APPROVED') LIMIT 1", nativeQuery = true)
	BookingDAO findUserOverlappingBooking(@Param("userId") String userId, @Param("roomId") String roomId,
			@Param("startAt") Timestamp startAt, @Param("endAt") Timestamp endAt);
}
