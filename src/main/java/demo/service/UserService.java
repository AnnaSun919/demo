package demo.service;

import demo.common.json.CommonJson;

public interface UserService {

	public CommonJson createUser(String UserName, String Email, String password);

	public CommonJson login(String username, String password);

}
