package com.telemedicine.authentication.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginDetails {
    private String email;
    private String userName;
    @NotBlank(message = "password is required")
    private String password;
}
