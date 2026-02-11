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
import demo.service.GroupService;
import demo.service.UserService;
import demo.common.utils.GeneralUtil;

@RestController
@ControllerAdvice
public class GroupController extends ApiController {
	
	@Autowired
	private GroupService groupService;

	@RequestMapping(value = RestURIConstants.CREATEGROUP, method = RequestMethod.POST)
	public CommonJson CreateGroup(HttpServletRequest request, @RequestBody CommonJson inputJson) throws Exception {

		return null ;
	}
	


}
