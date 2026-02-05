package demo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import demo.common.PasswordHelper;
import demo.common.json.CommonJson;
import demo.common.utils.GeneralUtil;
import demo.db.main.persistence.domain.UserDAO;
import demo.db.main.persistence.repository.UserRepository;
import demo.security.AppToken;
import demo.security.TokenHelper;

public class UserEventHandler implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@Override
    public CommonJson createUser(String Name, String Email, String Password) {
		CommonJson object = new CommonJson();
		
    	try {
    		UserDAO user = new UserDAO();
    		String uuid = UUID.randomUUID().toString();
    		user.setUserId(uuid);
    		user.setName(Name);
    		user.setEmail(Email);
    		
    		String encryptedPassword = passwordHelper.encryptPassword(Password);
    		user.setPassword(encryptedPassword);
    		
    		UserDAO existedUser = userRepository.findByName(Name);
    		
    		
    		if(existedUser != null) {
    			object.set("errCode", GeneralUtil.ERRCODE_REQUEST_FAIL);
    			object.set("errMessage", "User is already existed");
    			
    			return object; 
    		}
   
    		userRepository.save(user);
    		object.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL);
    		return object; 
    		
    	}catch (Exception e) {
    		object.set("errCode", GeneralUtil.ERRCODE_REQUEST_FAIL);
			object.set("errMessage", e);
    		return object; 
    	}
    }

	@Override
	public CommonJson login(String username, String password) {
		CommonJson userJson = new CommonJson();
		UserDAO user = userRepository.findByName(username);

		if(user == null||!passwordHelper.checkPassword(password, user.getPassword())) {
			return userJson.set("success",Boolean.FALSE).set("errMsg", "No User");
		}
		
		AppToken apptoken = new AppToken();
		Instant expiresDate = Instant.now().plus(500, ChronoUnit.MINUTES);
		apptoken.setUserId(user.getUserId());
		apptoken.setExpiresDate(expiresDate);
		Optional<String> token = tokenHelper.encrypt(apptoken);
		
		//check admin role and group here
		// if not admin // not in group == unauthorized 

		return userJson
			.set("auth", new CommonJson().set("token", token))
			.set("success",Boolean.TRUE)
			.set(
				"admin",
				new CommonJson()
					.set("id", user.getUserId())
					.set("email", user.getEmail())
					.set("name", user.getName())
			);

	}

}
