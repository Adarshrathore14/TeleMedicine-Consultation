package com.telemedicine.patient.dto;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PatientDto {
    private String patientId;
    private String userName;
    private String email;
    private LocalDate dateOfBirth;
    private String mobileNumber;
}
