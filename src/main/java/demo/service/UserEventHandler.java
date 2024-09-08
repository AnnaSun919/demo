package demo.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import demo.common.PasswordHelper;
import demo.common.json.CommonJson;
import demo.db.main.persistence.domain.UserDAO;
import demo.db.main.persistence.repository.UserRepository;

public class UserEventHandler implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Override
    public String createUser(String Name, String Email, String Password) {
    	try {

    		UserDAO user = new UserDAO();
    		
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

		String uuid = UUID.randomUUID().toString();

		return new CommonJson()
			.set("auth", new CommonJson().set("token", uuid))
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
