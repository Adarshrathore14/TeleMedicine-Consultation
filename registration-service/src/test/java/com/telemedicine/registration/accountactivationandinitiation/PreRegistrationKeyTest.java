package com.telemedicine.registration.accountactivationandinitiation;

import com.telemedicine.registration.configurations.HospitalMessageConfiguration;
import com.telemedicine.registration.configurations.ScheduledTimeConfiguration;
import com.telemedicine.registration.exceptions.InvalidMobileNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PreRegistrationKeyTest {
    @Mock
    private ScheduledTimeConfiguration scheduledTimeConfiguration;
    @Mock
    private HospitalMessageConfiguration hospitalMessageConfiguration;
    @Mock
    private Random random;
    @InjectMocks
    private PreRegistrationKey preRegistrationKey;

//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    void generateActivationCode() {
        String mobileNumber = "1234567890";
        Mockito.when(hospitalMessageConfiguration.getCode()).thenReturn("AbcHospital");
        String activationCode = preRegistrationKey.generateActivationCode(mobileNumber);
        assertNotNull(activationCode);
        assertTrue(activationCode.contains("AbcHospital"));
    }
    @Test
    void checkCodePositiveCase() throws InvalidMobileNumberException {
        String mobileNumber = "1234567890";
        String activationCode = preRegistrationKey.generateActivationCode(mobileNumber);
        assertTrue(preRegistrationKey.checkCode(mobileNumber, activationCode));
    }
    @Test
    void checkCodeNegativeCase() throws InvalidMobileNumberException {
        String mobileNumber = "1234567890";
        String activationCode = preRegistrationKey.generateActivationCode(mobileNumber);
        assertFalse(preRegistrationKey.checkCode(mobileNumber, "dummy"));
    }
    @Test
    void invalidMobileNumber(){
        assertThrows(InvalidMobileNumberException.class,()->preRegistrationKey.checkCode(null,"dummy"));
    }
}