package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.common.PasswordHelper;
import demo.security.TokenHelper;
import demo.service.RoomEventHandler;
import demo.service.RoomService;
import demo.service.UserEventHandler;
import demo.service.UserService;

@Configuration
public class CoreConfig {

    @Bean
    public PasswordHelper passwordHelper() {
    	return new PasswordHelper();
    }
    
    @Bean
    public UserService UserService() {
        return new UserEventHandler();
    }
    
    @Bean
    public RoomService RoomService() {
        return new RoomEventHandler();
    }
    
    @Bean
    public TokenHelper TokenHelper() {
    	return new TokenHelper();
    }

}

