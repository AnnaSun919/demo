package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.common.constants.RestURIConstants;
import demo.common.json.CommonJson;
import demo.common.utils.GeneralUtil;
import demo.db.main.persistence.domain.RoomDAO;
import demo.service.RoomService;

@RestController
@ControllerAdvice
public class RoomController {

	@Autowired
	private RoomService roomService;

	@RequestMapping(value = RestURIConstants.ROOMS, method = RequestMethod.GET)
	public CommonJson GetRooms(HttpServletRequest request) throws Exception {
		CommonJson rooms = new CommonJson();

		List<RoomDAO> listOfRooms = roomService.getRooms();

		return rooms.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("rooms", listOfRooms).set("success",
				Boolean.TRUE);
	}
	
	@RequestMapping(value = RestURIConstants.ROOM , method = RequestMethod.GET)
	public CommonJson GetRoomById(HttpServletRequest request, @RequestParam("roomId") String roomId) throws Exception {
		CommonJson rooms = new CommonJson();

		CommonJson room = roomService.getRoomById(roomId);

		return rooms.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("room", room ).set("success",
				Boolean.TRUE);
	}

	@RequestMapping(value = RestURIConstants.ADDROOMS, method = RequestMethod.POST)
	public CommonJson addRoom(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String name = inputJson.get("name");
		String description = inputJson.get("description");
		String capacity = inputJson.get("capacity");
		String status = inputJson.get("status");
		String isPublic = String.valueOf(inputJson.getOrDefault("isPublic", (Object) null));
		JSONArray groupIds = inputJson.getJSONArray("groupIds");
		
		CommonJson result = roomService.addRoom(name, description, capacity, status, isPublic, groupIds);

		return result.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("success",
				Boolean.TRUE);
	}

	@RequestMapping(value = RestURIConstants.USERAVAILABLEROOMS, method = RequestMethod.GET)
	public CommonJson getUserAvalibleRooms(HttpServletRequest request, @RequestParam("userId") String userId)
			throws Exception {
		CommonJson rooms = new CommonJson();

		List<RoomDAO> listOfRooms = roomService.findUserAvailableRooms(userId);

		return rooms.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("rooms", listOfRooms).set("success",
				Boolean.TRUE);

	}

	@RequestMapping(value = RestURIConstants.USERROOMAVAILABLEROOMTIMESLOT, method = RequestMethod.GET)
	public CommonJson getUserAvalibleRoomTimeslot(HttpServletRequest request, @RequestParam("userId") String userId, @RequestParam("roomId") String roomId,
			@RequestParam("date") String date) throws Exception {
		CommonJson timeslot = new CommonJson();

		List<CommonJson> availableTimeslot = roomService.getRoomAvailableTimeSlot(userId, roomId, date);

		return timeslot.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("timeslots", availableTimeslot)
				.set("success", Boolean.TRUE);

	}

	@RequestMapping(value = RestURIConstants.USERBOOKROOMS, method = RequestMethod.POST)
	public CommonJson BookRoom(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String userId = inputJson.get("userId");
		String roomId = inputJson.get("roomId");
		JSONArray timeslots = inputJson.getJSONArray("timeslots");

		return roomService.bookRoom(userId, roomId, timeslots);
	}
	
	@RequestMapping(value = RestURIConstants.DELETEROOM , method = RequestMethod.DELETE)
	public CommonJson deleteRoom(HttpServletRequest request, @RequestParam("roomId") String roomId) throws Exception {
	    CommonJson resultJson = new CommonJson();

	    	roomService.deleteRoom(roomId);

	        resultJson.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("success", Boolean.TRUE);


	    return resultJson;
	}

}
