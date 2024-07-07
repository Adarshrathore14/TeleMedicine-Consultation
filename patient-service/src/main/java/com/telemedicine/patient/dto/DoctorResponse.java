package com.telemedicine.patient.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
	private int doctorId;
	private String doctorName;
	private double consultantFee;
}
