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
import demo.common.constants.RestURIConstants;
import demo.common.json.CommonJson;
import demo.service.UserService;
import demo.common.utils.GeneralUtil;

@RestController
@ControllerAdvice
public class UserController extends ApiController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = RestURIConstants.LOGIN, method = RequestMethod.POST)
	public CommonJson UserLogin(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		jsonSchemaValidate(request,inputJson);
		String username = inputJson.get("username");
		String password = inputJson.get("password");
		
		CommonJson user = userService.login(username, password );
		
		//will have other user status
		return user!=null ? 
				user.set("errCode",GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL ): 
				new CommonJson().set("errCode",GeneralUtil.ERRCODE_REQUEST_FAIL) ;
	}
	
	@RequestMapping(value = RestURIConstants.CREATE_USER, method = RequestMethod.POST)
	public CommonJson CreateUser(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {
		jsonSchemaValidate(request,inputJson);
		String username = inputJson.get("username");
		String email = inputJson.get("email");
		String password = inputJson.get("password");
		
		return userService.createUser(username, email, password);
	}

}
