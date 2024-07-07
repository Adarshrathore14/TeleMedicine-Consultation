package com.telemedicine.registration.configurations;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@ConfigurationProperties("scheduled")
@Component
@Data
public class ScheduledTimeConfiguration {
    private String time;
    private long activationCodeExpirationTime;
}
