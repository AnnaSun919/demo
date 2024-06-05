package demo;


import java.util.Arrays;

import javax.servlet.ServletContext;


import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import ch.qos.logback.ext.spring.web.WebLogbackConfigurer;

import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class
	})
public class DemoApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
	


}
