package com.telemedicine.registration.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.telemedicine.registration.dto.InitiateRegistrationRequestDto;
import com.telemedicine.registration.dto.PatientDto;
import com.telemedicine.registration.entity.PatientEntity;
import com.telemedicine.registration.service.PatientRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PatientRegistrationControllerTest {
    @Mock
    private PatientRegistrationService patientRegistrationService;
    @InjectMocks
    private PatientRegistrationController patientRegistrationController;
    private MockMvc mockMvc;
    private String requestBody;
    private ObjectMapper objectMapper;
    private InitiateRegistrationRequestDto initiateRegistrationRequestDto;
    private PatientEntity patient;
    private PatientDto patientDto;
    @BeforeEach
    public void setUp(){
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        initiateRegistrationRequestDto = new InitiateRegistrationRequestDto();
        initiateRegistrationRequestDto.setMobileNumber("1234567890");
        patient = new PatientEntity("123","patientName","pat20","patient@gmail.com",
                LocalDate.parse("2000-11-01"),"1234567890","password","patient","pending","123",
                LocalDateTime.now(),null);
        patientDto = new PatientDto();
        patientDto.setDateOfBirth(LocalDate.now());
        patientDto.setEmail(patient.getEmail());
        patientDto.setMobileNumber(patient.getMobileNumber());
        patientDto.setPatientId(patient.getPatientId());
        patientDto.setUserName(patient.getUserName());
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientRegistrationController)
                .build();

    }
    @Test
    void initiateRegistration() throws Exception{
        when(patientRegistrationService.initiateRegistration(initiateRegistrationRequestDto)).thenReturn("code");
        requestBody = objectMapper.writeValueAsString(initiateRegistrationRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/initiate")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    void createProfile() throws Exception {
        when(patientRegistrationService.generateActivationLink(patient)).thenReturn("activationUrl");
        requestBody = objectMapper.writeValueAsString(patient);
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void activateAccount() throws Exception{
        when(patientRegistrationService.activateAccount("dummyToken")).thenReturn(patientDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/activate")
                        .param("activationToken","dummyToken"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}