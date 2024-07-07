package com.telemedicine.appointment.configurations;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("payment")
@Component
@Data
public class PaymentConfiguration {
    private String pending;
    private String paid;
}
