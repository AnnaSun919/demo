package demo.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;

import demo.db.main.persistence.domain.UserDAO;
import demo.db.main.persistence.repository.UserRepository;

public class UserEventHandler implements UserService {
	
	HashMap<String, String> userInfo = new HashMap<String, String>();
	
	UserDAO user = new UserDAO();
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public void createUser(String Name, String Email, String Password) {
    	try {

    		UserDAO user = new UserDAO();
    		
    		user.setName(Name);
    		user.setEmail(Email);
    		user.setPassword(Password);
    		
    		userRepository.save(user);
    	}catch (Exception e) {
    		System.out.println("tesitng save error" + e);
    	}
    }

	@Override
	public String login(String username, String password) {
		if(password.equals(userInfo.get(username.toLowerCase()))) {
			return "Login Sucess";
		}

		return "Incorrect Username or password";
	}

	

}
