package com.telemedicine.billing;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.billing.dto.AppointmentDto;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
public class CustomDeserializer implements Deserializer<AppointmentDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public AppointmentDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data,AppointmentDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AppointmentDto deserialize(String topic, Headers headers, byte[] data) {
        try {
            return objectMapper.readValue(data,AppointmentDto.class);
        } catch (IOException e) {
        	 throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void close() {
        Deserializer.super.close();
    }
}