package demo.service;

public interface UserService {
	
	public String createUser(String UserName, String Email, String password);
	
	public String login(String username , String password);

}
