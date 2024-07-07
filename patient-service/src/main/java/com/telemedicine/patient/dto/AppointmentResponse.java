package com.telemedicine.patient.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentResponse {
    private int billNumber;
    private int appointmentId;
    private double consultationFees;
    private int doctorId;
    private String patientId;
    private LocalDate appointmentDate;
}
