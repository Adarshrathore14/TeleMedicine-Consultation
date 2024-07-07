package com.telemedicine.portal.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("ticket")
@Component
@Data
public class TicketNumberConfiguration {
    private int length;
    private String code;
}
