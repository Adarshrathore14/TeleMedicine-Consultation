package com.telemedicine.patient.dto;

import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	private String patientId;
	private String patientName;
	private String patientUserName;
	private String patientEmail;
	private String patientPhoneNo;
	
}
