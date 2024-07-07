package com.telemedicine.authentication.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt")
@Component
@Data
public class JwtConfiguration {
    private String secretKey;
    private int expirationTime;
}
