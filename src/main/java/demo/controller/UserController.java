package demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.common.PasswordHelper;
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
		String isLogined = username != null && password != null? userService.login(username, password ) : "Invalid Username and/or Password ";
		
		return isLogined;
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String CreateUser(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String username = StringUtils.isEmpty(inputJson.get("username"))? null : inputJson.get("username");
		String email = StringUtils.isEmpty(inputJson.get("email"))? null : inputJson.get("email");
		String password = StringUtils.isEmpty(inputJson.get("password"))? null : inputJson.get("password");
		
		if(username == null) {
			return "User name is missing";
		}
		
		if(email == null) {
			return "Email is missing";
		}
		
		if(password == null) {
			return "Password is missing";
		}
		
		return userService.createUser(username,email,password);
	}

}
