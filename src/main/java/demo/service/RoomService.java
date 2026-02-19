package demo.service;

import java.util.List;

import org.json.JSONArray;

import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.RoomDAO;

public interface RoomService {

	public CommonJson addRoom(String name, String description, String capacity, String status) throws Exception;

	public CommonJson bookRoom(String userId, String roomId, JSONArray timeslotsStr) throws Exception;

	public List<RoomDAO> getRooms();

	public List<RoomDAO> findUserAvailableRooms(String userId);

	public List<CommonJson> getRoomAvailableTimeSlot(String userId, String roomId, String string);

}
