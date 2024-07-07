package com.telemedicine.DoctorService.dtos;

import com.telemedicine.DoctorService.model.PatientEntity;
import lombok.Data;
@Data
public class AppointmentResponse {
    private int appointmentId;
    private int doctorId;
    private PatientEntity patientDetails;
}
