package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.service.*;

@Configuration
public class CoreConfig {

    @Bean
    public UserService UserService() {
        return new UserEventHandler();
    }
    
    @Bean
    public MeetingService MeetingService() {
        return new MeetingEventHandler();
    }

}

