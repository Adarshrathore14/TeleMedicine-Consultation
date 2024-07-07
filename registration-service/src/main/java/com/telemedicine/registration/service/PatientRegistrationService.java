package com.telemedicine.registration.service;
import com.telemedicine.registration.dto.InitiateRegistrationRequestDto;
import com.telemedicine.registration.dto.PatientDto;
import com.telemedicine.registration.entity.PatientEntity;
import com.telemedicine.registration.exceptions.*;

public interface PatientRegistrationService {
    String initiateRegistration(InitiateRegistrationRequestDto initiateRegistrationRequest) throws MobileNumberAlreadyExistsException;
    String generateActivationLink(PatientEntity patient) throws ActivationCodeException, InvalidMobileNumberException, UserNameAndEmailAlreadyExistsException;
    PatientDto activateAccount(String activationToken) throws AccountActivationException;
}
