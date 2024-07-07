package com.telemedicine.registration.entity;
import com.telemedicine.registration.configurations.PatientConfiguration;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PatientIdGeneratorTest {

    @Mock
    private SharedSessionContractImplementor mockSession;
    @Mock
    private Object mockobject;
    @Mock
    private Random random;
    @Mock
    private PatientConfiguration patientConfiguration;
    @InjectMocks
    private PatientIdGenerator patientIdGenerator;

    @Test
    void generate() {
        when(patientConfiguration.getCode()).thenReturn("abc");
        when(patientConfiguration.getIdLength()).thenReturn(5);
        assertInstanceOf(Object.class,patientIdGenerator.generate(mockSession,mockobject));
        assertNotNull(patientIdGenerator.generate(mockSession,mockobject));
    }

}