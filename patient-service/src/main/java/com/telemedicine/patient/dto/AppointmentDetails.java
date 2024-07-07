package com.telemedicine.patient.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDetails {
    private int appointmentId;
    private double consultationFees;
    private int doctorId;
    private String patientId;
    private LocalDate appointmentDate;
}
