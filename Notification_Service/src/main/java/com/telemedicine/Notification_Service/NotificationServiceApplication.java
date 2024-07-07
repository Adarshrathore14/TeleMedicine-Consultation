package com.telemedicine.Notification_Service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.telemedicine.Notification_Service,com.telemedicine.authorization"})
@EnableConfigurationProperties
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

//	@Bean
//	public ModelMapper mapper() {
//		return new ModelMapper();
//	}
}
