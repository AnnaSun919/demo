package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.common.constants.RestURIConstants;
import demo.common.json.CommonJson;
import demo.common.utils.GeneralUtil;
import demo.db.main.persistence.domain.RoomDAO;
import demo.db.main.persistence.domain.TimeslotDAO;
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

	@RequestMapping(value = RestURIConstants.ADDROOMS, method = RequestMethod.POST)
	public CommonJson addRoom(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String name = inputJson.get("name");
		String description = inputJson.get("description");
		String capacity = inputJson.get("capacity");
		String status = inputJson.get("status");

		return roomService.addRoom(name, description, capacity, status);
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
	public CommonJson getUserAvalibleRoomTimeslot(HttpServletRequest request, @RequestParam("roomId") String roomId,
			@RequestParam("date") String date) throws Exception {
		CommonJson timeslot = new CommonJson();

		List<CommonJson> availableTimeslot = roomService.getRoomAvailableTimeSlot(roomId, "2026-06-02");

		return timeslot.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("rooms", availableTimeslot)
				.set("success", Boolean.TRUE);

	}

//	@RequestMapping(value = RestURIConstants.BOOKROOMS, method = RequestMethod.POST)
//	public String BookRoom(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
//		String userId = StringUtils.isEmpty(inputJson.get("userId")) ? null : inputJson.get("userId");
//		String groupId = StringUtils.isEmpty(inputJson.get("groupId")) ? null : inputJson.get("groupId");
//		String startAt = StringUtils.isEmpty(inputJson.get("startAt")) ? null : inputJson.get("startAt");
//		String endAt = StringUtils.isEmpty(inputJson.get("endAt")) ? null : inputJson.get("endAt");
//
//		return roomService.bookRoom(userId, groupId, startAt, endAt);
//	}

}
