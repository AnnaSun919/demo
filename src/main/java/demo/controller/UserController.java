package demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.common.json.CommonJson;
import demo.service.UserService;

@RestController
@ControllerAdvice
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String UserLogin(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String username = StringUtils.isEmpty(inputJson.get("username"))? null : inputJson.get("username");
		String password = StringUtils.isEmpty(inputJson.get("password"))? null : inputJson.get("password");
		String userInfo = username != null && password != null? userService.login(username, password ) :"Missing Username and Password ";
		
		return userInfo;
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String CreateUser(HttpServletRequest request) throws Exception {
		userService.createUser("test","test@gmail","123456789");
		return "createUser success!?";
	}

}
