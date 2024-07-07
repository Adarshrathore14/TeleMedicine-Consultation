package com.telemedicine.registration.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("hospital")
@Component
@Data
public class HospitalMessageConfiguration {
    private String code;
}
