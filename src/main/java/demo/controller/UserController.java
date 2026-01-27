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
import demo.common.utils.GeneralUtil;

@RestController
@ControllerAdvice
public class UserController extends ApiController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public CommonJson UserLogin(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		String username = StringUtils.isEmpty(inputJson.get("username"))? null : inputJson.get("username");
		String password = StringUtils.isEmpty(inputJson.get("password"))? null : inputJson.get("password");
		CommonJson user = username != null && password != null? userService.login(username, password ) : null;
		
		//will have other user status
		return user!=null ? 
				user.set("errCode",GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL ): 
				new CommonJson().set("errCode",GeneralUtil.ERRCODE_REQUEST_FAIL) ;
	}
	
	@RequestMapping(value = "/create-user", method = RequestMethod.POST)
	public CommonJson CreateUser(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		jsonSchemaValidate(request,inputJson);
		String username = StringUtils.isEmpty(inputJson.get("username"))? null : inputJson.get("username");
		String email = StringUtils.isEmpty(inputJson.get("email"))? null : inputJson.get("email");
		String password = StringUtils.isEmpty(inputJson.get("password"))? null : inputJson.get("password");
		
		return userService.createUser(username, email, password);
	}

}
