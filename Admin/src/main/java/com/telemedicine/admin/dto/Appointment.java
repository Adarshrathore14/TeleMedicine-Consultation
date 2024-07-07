package com.telemedicine.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
	private int appointmentId;
	private String patientId;
	private int slotId;
	private int doctorId;
	private LocalDate appointmentDate;
	private boolean notificationStatus;
	private LocalDateTime modifiedTime;
}

