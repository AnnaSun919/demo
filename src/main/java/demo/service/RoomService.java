package demo.service;

import java.util.List;
import demo.db.main.persistence.domain.RoomDAO;

public interface RoomService {

	public String createRoom(String name, String description, String capacity, String groupId);
	
	public String bookRoom(String userId, String groupId, String startAt, String endAt);
	
	public List<RoomDAO> getRooms();
	
	

}
