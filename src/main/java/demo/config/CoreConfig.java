package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.common.PasswordHelper;
import demo.service.*;

@Configuration
public class CoreConfig {

    @Bean
    public UserService UserService() {
        return new UserEventHandler();
    }
    
    @Bean
    public PasswordHelper passwordHelper() {
    	return new PasswordHelper();
    }
    
    @Bean
    public RoomService RoomService() {
        return new RoomEventHandler();
    }

}

