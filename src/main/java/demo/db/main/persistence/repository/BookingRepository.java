package demo.db.main.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.db.main.persistence.domain.BookingDAO;

public interface BookingRepository extends JpaRepository<BookingDAO, Integer> {

	@Query(value = "SELECT b.Id, b.title, b.status, b.start_At, b.end_At, r.name AS roomName "
			+ "FROM BOOKING b JOIN Room r ON b.roomId = r.Id " + "WHERE b.userId = :userId", nativeQuery = true)
	public List<Object[]> findBookingByUserId(@Param("userId") String userId);

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

	public BookingDAO findBybookingId(String bookingId);
}
