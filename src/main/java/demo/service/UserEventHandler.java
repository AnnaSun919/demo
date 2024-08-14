package demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.PasswordHelper;
import demo.db.main.persistence.domain.UserDAO;
import demo.db.main.persistence.repository.UserRepository;

public class UserEventHandler implements UserService {
	
	UserDAO user = new UserDAO();
	
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
    		System.out.println("tesitng save error" + e);
    		return "Error occurs " + e; 
    	}
    }

	@Override
	public String login(String username, String password) {
		
		UserDAO user = userRepository.findByName(username);
		
		if(passwordHelper.checkPassword(password,user.getPassword())) {
			return "login success";
		}
		
		return "Invalid Username and/or Password ";
	}

	

}
