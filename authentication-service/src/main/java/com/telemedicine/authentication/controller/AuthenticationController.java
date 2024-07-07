package com.telemedicine.authentication.controller;
import com.telemedicine.authentication.apidefinitions.AuthenticationApiDefinition;
import com.telemedicine.authentication.dto.LoginDetails;
import com.telemedicine.authentication.service.AuthenticationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.security.auth.login.AccountNotFoundException;
@RestController
@AllArgsConstructor
@RequestMapping("/authenticate")
public class AuthenticationController implements AuthenticationApiDefinition {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    @Override
    public ResponseEntity<String> login(@RequestBody @Valid LoginDetails loginDetails) throws AccountNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(loginDetails));
    }
}
