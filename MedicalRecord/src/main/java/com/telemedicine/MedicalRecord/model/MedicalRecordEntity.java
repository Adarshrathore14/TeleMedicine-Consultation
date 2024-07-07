package com.telemedicine.MedicalRecord.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int medicalId;
	private String medicalDescription;
	private int appointmentId;
	private String patientId;
	private int doctorId;
}
