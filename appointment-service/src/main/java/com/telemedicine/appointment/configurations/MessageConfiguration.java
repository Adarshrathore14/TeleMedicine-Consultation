package com.telemedicine.appointment.configurations;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("message")
@Component
@Data
public class MessageConfiguration {
    private String success;
}
