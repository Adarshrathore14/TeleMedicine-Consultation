package com.telemedicine.appointment.customserde;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.telemedicine.appointment.dto.AppointmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
class CustomSerializerTest {
    private CustomSerializer customSerializer;
    @BeforeEach
    void setUp(){
        customSerializer = new CustomSerializer();
    }

    @Test
    void serialize() throws JsonProcessingException {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(1);
        appointmentDto.setConsultationFees(124.00);
        byte[] serializedData = customSerializer.serialize("topic", appointmentDto);
        String string = Arrays.toString(serializedData);
        assertNotNull(string);
    }
    @Test
    void testSerializeWithHeaders() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(1);
        appointmentDto.setConsultationFees(124.00);
        byte[] serializedData = customSerializer.serialize("topic",null,appointmentDto);
        String string = Arrays.toString(serializedData);
        assertNotNull(string);
    }
}