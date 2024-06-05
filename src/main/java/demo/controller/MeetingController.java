package demo.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.common.json.CommonJson;
import demo.service.MeetingService;
import demo.service.UserService;

@RestController
@ControllerAdvice
public class MeetingController {
	
	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping(value = "/meeting/join", method = RequestMethod.POST)
	public String UserLogin(HttpServletRequest request, @RequestParam String username) throws Exception {

		String UserInfo = meetingService.join("test");

		return UserInfo;
	}
	
	@RequestMapping(value = "/meeting", method = RequestMethod.GET)
	public String userGet(HttpServletRequest request) throws Exception {
	
		return "get Meeting!?";
	}

}
