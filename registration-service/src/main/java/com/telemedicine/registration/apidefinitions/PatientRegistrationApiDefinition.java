package com.telemedicine.registration.apidefinitions;
import com.telemedicine.registration.dto.InitiateRegistrationRequestDto;
import com.telemedicine.registration.dto.PatientDto;
import com.telemedicine.registration.entity.PatientEntity;
import com.telemedicine.registration.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
@Tag(name = "Registration",description = "Patient Registration for consulting with the Doctor")
public interface PatientRegistrationApiDefinition {
    @Operation(summary = "Initiate Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Activation code is Generated")
    })
    ResponseEntity<String> initiateRegistration(@Valid @RequestBody InitiateRegistrationRequestDto
                                                        initiateRegistrationRequest) throws MobileNumberAlreadyExistsException;
    @Operation(summary = "Entering the Registration Form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Activation Token is generated to activate the account"),
            @ApiResponse(responseCode = "404",description = "activation code is expired or invalid activation code"),
            @ApiResponse(responseCode = "404",description = "Mobile number given is invalid one"),
            @ApiResponse(responseCode = "406",description = "validation Errors"),
            @ApiResponse(responseCode = "409",description = "account already exists")
    })
    ResponseEntity<String> createProfile(@Valid @RequestBody PatientEntity patient) throws ActivationCodeException,
            InvalidMobileNumberException, UserNameAndEmailAlreadyExistsException;
    @Operation(summary = "Account Activation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Activation Token is validated and account is activated"),
            @ApiResponse(responseCode = "401",description = "Activation Token is expired")
    })
    ResponseEntity<PatientDto> activateAccount(@RequestParam String activationToken)
            throws AccountActivationException;

}
