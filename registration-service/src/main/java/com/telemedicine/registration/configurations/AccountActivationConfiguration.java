package com.telemedicine.registration.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("account-activation")
@Component
@Data
public class AccountActivationConfiguration {
    private String secretKey;
    private String baseUrl;
    private long expirationTime;
}
