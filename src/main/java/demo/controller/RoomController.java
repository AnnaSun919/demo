package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		
		return rooms.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL)
				.set("rooms", listOfRooms)
				.set("success", Boolean.TRUE);
	}
	
	@RequestMapping(value = RestURIConstants.ADDROOMS, method = RequestMethod.POST)
	public String CreateRoom(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String name = StringUtils.isEmpty(inputJson.get("name"))? null : inputJson.get("name");
		String description = StringUtils.isEmpty(inputJson.get("description"))? null : inputJson.get("description");
		String capacity = StringUtils.isEmpty(inputJson.get("groupId"))? null : inputJson.get("groupId");
		String groupId = StringUtils.isEmpty(inputJson.get("groupId"))? null : inputJson.get("groupId");
		
		
		
		return roomService.createRoom(name ,description, capacity ,groupId);
	}

}
