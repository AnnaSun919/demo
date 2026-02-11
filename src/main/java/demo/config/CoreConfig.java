package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.common.PasswordHelper;
import demo.security.TokenHelper;
import demo.service.BookingEventHandler;
import demo.service.BookingService;
import demo.service.RoomEventHandler;
import demo.service.RoomService;
import demo.service.UserEventHandler;
import demo.service.UserService;
import demo.service.GroupEventHandler;
import demo.service.GroupService;

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
    public BookingService BookingService() {
        return new BookingEventHandler();
    }
    
    @Bean
    public GroupService GroupService() {
        return new GroupEventHandler();
    }
    
    @Bean
    public TokenHelper TokenHelper() {
    	return new TokenHelper();
    }

}

