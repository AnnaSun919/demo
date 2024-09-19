package demo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import demo.common.PasswordHelper;
import demo.common.json.CommonJson;
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
    public String createUser(String Name, String Email, String Password) {
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
    			return "User Name has been registered";
    		}
   
    		userRepository.save(user);
    		return "regristration success"; 
    		
    	}catch (Exception e) {
    		return "Error occurs " + e; 
    	}
    }

	@Override
	public CommonJson login(String username, String password) {
		
		UserDAO user = userRepository.findByName(username);
		
		if(user == null||!passwordHelper.checkPassword(password, user.getPassword())) {
			return null;
		}
		
		AppToken apptoken = new AppToken();
		Instant expiresDate = Instant.now().plus(5, ChronoUnit.MINUTES);
		apptoken.setUserId(user.getUserId());
		apptoken.setExpiresDate(expiresDate);
		Optional<String> token = tokenHelper.encrypt(apptoken);

		return new CommonJson()
			.set("auth", new CommonJson().set("token", token))
			.set(
				"admin",
				new CommonJson()
					.set("id", user.getUserId())
					.set("email", user.getEmail())
					.set("name", user.getName())
			);
		
		
		//check admin role and group here
		// if not admin // not in group == unauthorized 

	}

	

}
