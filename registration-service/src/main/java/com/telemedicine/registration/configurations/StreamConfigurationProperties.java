package com.telemedicine.registration.configurations;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.kafka.streams")
@Data
public class StreamConfigurationProperties {
    private List<String> bootstrapServers;
    private String applicationId;
}