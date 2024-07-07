package com.telemedicine.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.telemedicine.portal,com.telemedicine.authorization"})
@EnableConfigurationProperties
public class PortalServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PortalServiceApplication.class, args);
	}

}
