package com.telemedicine.registration.configurations;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("patient")
@Component
@Data
public class PatientConfiguration {
    private String role;
    private String code;
    private int idLength;
}
