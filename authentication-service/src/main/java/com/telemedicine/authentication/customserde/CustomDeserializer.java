package com.telemedicine.authentication.customserde;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.exceptions.DeserializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomDeserializer implements Deserializer<AuthenticationEntity> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public AuthenticationEntity deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data,AuthenticationEntity.class);
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage());
        }
    }

    @Override
    public AuthenticationEntity deserialize(String topic, Headers headers, byte[] data) {
        try {
            return objectMapper.readValue(data,AuthenticationEntity.class);
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage());
        }
    }
    @Override
    public void close() {
        Deserializer.super.close();
    }
}