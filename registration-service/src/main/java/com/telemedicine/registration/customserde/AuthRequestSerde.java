package com.telemedicine.registration.customserde;
import com.telemedicine.registration.dto.AuthRequestDto;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
@Component
public class AuthRequestSerde implements Serde<AuthRequestDto> {
    @Autowired
    private CustomDeserializer customDeserializer;
    @Autowired
    private CustomSerializer customSerializer;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serde.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Serde.super.close();
    }

    @Override
    public Serializer<AuthRequestDto> serializer() {
        return customSerializer;
    }

    @Override
    public Deserializer<AuthRequestDto> deserializer() {
        return customDeserializer;
    }
}
