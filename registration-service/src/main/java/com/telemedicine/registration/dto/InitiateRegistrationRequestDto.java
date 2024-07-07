package com.telemedicine.registration.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class InitiateRegistrationRequestDto {
    @NotBlank(message = "mobile number is required")
    @Pattern(regexp = "[0-9]{10}",message = "mobileNumber is required")
    private String mobileNumber;
}
