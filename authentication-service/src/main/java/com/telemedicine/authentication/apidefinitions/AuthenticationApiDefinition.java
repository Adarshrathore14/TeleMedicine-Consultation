package com.telemedicine.authentication.apidefinitions;

import com.telemedicine.authentication.dto.LoginDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.AccountNotFoundException;
@Tag(name = "authentication",description = "Login functionality and Adding static UserName and Password for" +
        "Admin,Doctor and IT-Team")
public interface AuthenticationApiDefinition {
    @Operation(summary = "User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Authenticated and Token Created"),
            @ApiResponse(responseCode = "401",description = "UnAuthorized User,Credentials are not correct")
    })
    ResponseEntity<String> login(@Valid @RequestBody LoginDetails loginDetails) throws AccountNotFoundException;
}
