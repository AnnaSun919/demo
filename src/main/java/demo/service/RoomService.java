package demo.service;

import java.util.List;

import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.RoomDAO;

public interface RoomService {

	public CommonJson addRoom(String name, String description, String capacity, String status) throws Exception;
	
	public CommonJson bookRoom(List<CommonJson> inputJsonList)  throws Exception ;
	
	public List<RoomDAO> getRooms();
	
	public List<RoomDAO> findUserAvailableRooms(String userId);

	public List<CommonJson> getRoomAvailableTimeSlot(String roomId, String string);

}
