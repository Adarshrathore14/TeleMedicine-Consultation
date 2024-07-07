package com.telemedicine.MedicalRecord.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

	private int appointmentId;
	private int patientId;
	private int slotId;
	private int doctorId;
	private LocalDate appointmentDate;
	private boolean notificationStatus;
	private LocalDateTime lastUpdatedTime;
}
