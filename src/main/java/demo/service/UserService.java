package demo.service;

public interface UserService {
	
	public void createUser(String UserName, String Email, String password);
	
	public String login(String username , String password);

}
