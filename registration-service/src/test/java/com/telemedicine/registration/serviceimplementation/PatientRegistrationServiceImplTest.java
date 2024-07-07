package com.telemedicine.registration.serviceimplementation;

import com.telemedicine.registration.accountactivationandinitiation.ActivateAccount;
import com.telemedicine.registration.accountactivationandinitiation.PreRegistrationKey;
import com.telemedicine.registration.accountactivationandinitiation.ValidateAccountActivationToken;
import com.telemedicine.registration.configurations.AccountActivationConfiguration;
import com.telemedicine.registration.configurations.MessageConfiguration;
import com.telemedicine.registration.configurations.PatientConfiguration;
import com.telemedicine.registration.dto.AuthRequestDto;
import com.telemedicine.registration.dto.InitiateRegistrationRequestDto;
import com.telemedicine.registration.dto.PatientDto;
import com.telemedicine.registration.entity.PatientEntity;
import com.telemedicine.registration.exceptions.*;
import com.telemedicine.registration.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientRegistrationServiceImplTest {
    @Mock
    private PreRegistrationKey preRegistrationKey;
    @Mock
    private ActivateAccount activateAccount;
    @Mock
    private ValidateAccountActivationToken validateAccountActivationToken;
    @Mock
    private MessageConfiguration messageConfiguration;
    @Mock
    private PatientConfiguration patientConfiguration;
    @Mock
    private AccountActivationConfiguration accountActivationConfiguration;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private KafkaTemplate<String, AuthRequestDto> kafkaTemplate;
    @InjectMocks
    private PatientRegistrationServiceImpl patientRegistrationService;
    private InitiateRegistrationRequestDto initiateRegistrationRequestDto;
    private PatientEntity patient;
    private AuthRequestDto authRequestDto;
    private PatientDto patientDto;
    @BeforeEach
    void setUp() {
        initiateRegistrationRequestDto = new InitiateRegistrationRequestDto();
        initiateRegistrationRequestDto.setMobileNumber("1234567890");
        patient = new PatientEntity("123","patientName","patient@123","patient@gmail.com",
                LocalDate.now(),"1234567890","password","patient","pending","123",
                LocalDateTime.now(),null);
        authRequestDto = new AuthRequestDto();
        authRequestDto.setEmail(patient.getEmail());
        authRequestDto.setPassword(patient.getPassword());
        authRequestDto.setRole(patient.getRole());
        authRequestDto.setUserId(patient.getPatientId());
        authRequestDto.setUserName(patient.getUserName());
        patientDto = new PatientDto();
        patientDto.setDateOfBirth(LocalDate.now());
        patientDto.setEmail(patient.getEmail());
        patientDto.setMobileNumber(patient.getMobileNumber());
        patientDto.setPatientId(patient.getPatientId());
        patientDto.setUserName(patient.getUserName());
    }

    @Test
    void initiateRegistration() throws MobileNumberAlreadyExistsException {
        when(patientRepository.existsByMobileNumber(initiateRegistrationRequestDto.getMobileNumber())).thenReturn(false);
        when(preRegistrationKey.generateActivationCode(initiateRegistrationRequestDto.getMobileNumber())).
                thenReturn("activationCode");
        assertNotNull(patientRegistrationService.initiateRegistration(initiateRegistrationRequestDto));
        assertInstanceOf(String.class,patientRegistrationService.initiateRegistration(initiateRegistrationRequestDto));
    }
    @Test
    void initiateRegistrationNegativeCase(){
        when(patientRepository.existsByMobileNumber(initiateRegistrationRequestDto.getMobileNumber())).thenReturn(true);
        assertThrows(MobileNumberAlreadyExistsException.class,()->patientRegistrationService.initiateRegistration(initiateRegistrationRequestDto));
    }

    @Test
    void generateActivationLinkPositiveCase() throws InvalidMobileNumberException, ActivationCodeException, UserNameAndEmailAlreadyExistsException {
        when(preRegistrationKey.checkCode("1234567890","123")).thenReturn(true);
        when(patientRepository.existsByUserNameAndEmail(patient.getUserName(), patient.getEmail())).thenReturn(false);
        when(accountActivationConfiguration.getBaseUrl()).thenReturn("http://localhost:9095/activate");
        when(activateAccount.generateAccountActivationToken("patientName",patient)).thenReturn("token");
        assertNotNull(patientRegistrationService.generateActivationLink(patient));
    }
    @Test
    void generateActivationLinkNegativeCase() throws InvalidMobileNumberException {
        when(preRegistrationKey.checkCode("1234567890","123")).thenReturn(false);
        assertThrows(ActivationCodeException.class,()->patientRegistrationService.generateActivationLink(patient));
    }
    @Test
    void generateActivationLinkNegativeCaseAccountAlreadyExists() throws InvalidMobileNumberException {
        when(preRegistrationKey.checkCode("1234567890","123")).thenReturn(true);
        when(patientRepository.existsByUserNameAndEmail(patient.getUserName(), patient.getEmail())).thenReturn(true);
        assertThrows(UserNameAndEmailAlreadyExistsException.class,()->patientRegistrationService.generateActivationLink(patient));

    }

    @Test
    void activateAccount() throws AccountActivationException {
        when(validateAccountActivationToken.validateToken("code")).thenReturn(true);
        when(validateAccountActivationToken.extractPatientName("code")).thenReturn("patient");
        when(activateAccount.getPatientByPatientName("patient")).thenReturn(patient);
        when(messageConfiguration.getAccountActivated()).thenReturn("activated");
        when(patientConfiguration.getRole()).thenReturn("role");
        when(patientRepository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, AuthRequestDto.class)).thenReturn(authRequestDto);
        when(modelMapper.map(patient,PatientDto.class)).thenReturn(patientDto);
        kafkaTemplate.send("topic",authRequestDto);
        assertNotNull(patientRegistrationService.activateAccount("code"));
    }
    @Test
    void activateAccountNegativeCase(){
        when(validateAccountActivationToken.validateToken("code")).thenReturn(false);
        assertThrows(AccountActivationException.class,()-> patientRegistrationService.activateAccount("code"));
    }
}