package com.telemedicine.authentication.customserde;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.exceptions.DeserializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
class CustomDeserializerTest {
    private ObjectMapper mockObjectMapper;
    private CustomDeserializer customDeserializer;
    private AuthenticationEntity authenticationEntity;
    private byte[] data;
    @BeforeEach
    void setUp(){
        authenticationEntity = new AuthenticationEntity("123","userName","user@gmail.com",
                "user","patient");
        customDeserializer = new CustomDeserializer();
        mockObjectMapper = new ObjectMapper();
    }

    @Test
    void deserializeWithTopicAndData() throws IOException {
        data = mockObjectMapper.writeValueAsBytes(authenticationEntity);
        assertNotNull(customDeserializer.deserialize("topic",data));
    }
    @Test
    void deserializeWithTopicAndDataAndHeader() throws IOException {
        data = mockObjectMapper.writeValueAsBytes(authenticationEntity);
        assertNotNull(customDeserializer.deserialize("topic",null,data));
    }
    @Test
    void testDeserializeExceptionWithTopicAndData() throws IOException {
        assertThrows(DeserializationException.class,()->customDeserializer.deserialize("topic",new byte[0]));
    }
    @Test
    void testDeserializeExceptionWithTopicAndDataAndHeader() throws IOException {
        assertThrows(DeserializationException.class,()->customDeserializer.deserialize("topic",null,new byte[0]));
    }

}