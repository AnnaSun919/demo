package demo.common;
import org.jasypt.util.password.BasicPasswordEncryptor;


public class PasswordHelper {
	
	public boolean checkPassword(String userPassword) {
		
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);
		if (passwordEncryptor.checkPassword(userPassword, encryptedPassword)) {
		  return true;
		} 
		return false;
	}
	
	public String encryptPassword(String userPassword) {
		
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);
		return encryptedPassword;
		
	}
	
}
