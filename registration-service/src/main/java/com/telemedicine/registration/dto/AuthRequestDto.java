package com.telemedicine.registration.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String role;
}
