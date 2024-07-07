package com.telemedicine.registration.controller;
import com.telemedicine.registration.apidefinitions.PatientRegistrationApiDefinition;
import com.telemedicine.registration.dto.InitiateRegistrationRequestDto;
import com.telemedicine.registration.dto.PatientDto;
import com.telemedicine.registration.entity.PatientEntity;
import com.telemedicine.registration.exceptions.*;
import com.telemedicine.registration.service.PatientRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
public class PatientRegistrationController implements PatientRegistrationApiDefinition {
    private final PatientRegistrationService patientRegistrationService;
    @PostMapping("/initiate")
    public ResponseEntity<String> initiateRegistration(@Valid @RequestBody InitiateRegistrationRequestDto initiateRegistrationRequest)
            throws MobileNumberAlreadyExistsException {
        return ResponseEntity.ok(patientRegistrationService.initiateRegistration(initiateRegistrationRequest));
    }
    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@Valid @RequestBody PatientEntity patient)
            throws ActivationCodeException, InvalidMobileNumberException, UserNameAndEmailAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientRegistrationService.generateActivationLink(patient));
    }
    @GetMapping("/activate")
    public ResponseEntity<PatientDto> activateAccount(@RequestParam String activationToken)
            throws AccountActivationException {
        return ResponseEntity.ok(patientRegistrationService.activateAccount(activationToken));
    }
}
