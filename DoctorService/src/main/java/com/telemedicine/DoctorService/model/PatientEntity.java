package com.telemedicine.DoctorService.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {
    @Id
    private String patientId;
    private String patientName;
}
