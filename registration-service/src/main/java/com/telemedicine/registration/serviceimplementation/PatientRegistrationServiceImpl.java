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
import com.telemedicine.registration.service.PatientRegistrationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PatientRegistrationServiceImpl implements PatientRegistrationService {
    private final PreRegistrationKey preRegistrationKey;
    private final ActivateAccount activateAccount;
    private final ValidateAccountActivationToken validateAccountActivationToken;
    private final MessageConfiguration messageConfiguration;
    private final AccountActivationConfiguration accountActivationConfiguration;
    private final PatientConfiguration patientConfiguration;
    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private KafkaTemplate<String, AuthRequestDto> requestDtoKafkaTemplate;
    @Override
    public String initiateRegistration(InitiateRegistrationRequestDto initiateRegistrationRequest) throws
            MobileNumberAlreadyExistsException {
        if(Boolean.TRUE.equals(patientRepository.existsByMobileNumber(initiateRegistrationRequest.getMobileNumber()))){
            throw new MobileNumberAlreadyExistsException("account already exists");
        }
        return preRegistrationKey.generateActivationCode(initiateRegistrationRequest.getMobileNumber());
    }

    @Override
    public String generateActivationLink(PatientEntity patient)
            throws ActivationCodeException, InvalidMobileNumberException, UserNameAndEmailAlreadyExistsException {
        if(!preRegistrationKey.checkCode(patient.getMobileNumber(),patient.getActivationCode())){
            throw new ActivationCodeException("Activation token is expired or mobile number is incorrect");
        }
        if(Boolean.TRUE.equals(patientRepository.existsByUserNameAndEmail(patient.getUserName(),patient.getEmail()))){
            throw new UserNameAndEmailAlreadyExistsException("account already exists");
        }
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(accountActivationConfiguration.getBaseUrl())
                .queryParam("activationToken", activateAccount.generateAccountActivationToken(patient.getPatientName(),patient));
        return uriBuilder.toUriString();
    }
    @Override
    public PatientDto activateAccount(String activationToken)
            throws AccountActivationException {
        if(activationToken ==null || !validateAccountActivationToken.validateToken(activationToken)){
            throw new AccountActivationException("either token is null or token is expired");
        }
        String patientName = validateAccountActivationToken.extractPatientName(activationToken);
        PatientEntity patient = activateAccount.getPatientByPatientName(patientName);
        patient.setRole(patientConfiguration.getRole());
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setCreatedDate(LocalDateTime.now());
        patient.setAccountStatus(messageConfiguration.getAccountActivated());
        patientRepository.save(patient);
        PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);
        AuthRequestDto authEvent = modelMapper.map(patient,AuthRequestDto.class);
        authEvent.setUserId(patient.getPatientId());
        requestDtoKafkaTemplate.send("registration-topic",authEvent);
        return patientResponse;
    }
}