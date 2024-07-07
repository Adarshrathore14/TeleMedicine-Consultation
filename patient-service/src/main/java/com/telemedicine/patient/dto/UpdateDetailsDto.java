package com.telemedicine.patient.dto;

import lombok.Data;

@Data
public class UpdateDetailsDto {
    private String userName;
    private String password;
    private String email;
}
