package com.telemedicine.Video_Call_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.telemedicine.Video_Call_Service,com.telemedicine.authorization"})
@EnableConfigurationProperties
public class VideoCallServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoCallServiceApplication.class, args);
	}

}
