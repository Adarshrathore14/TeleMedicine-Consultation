package com.telemedicine.admin.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailsResponse {
	private int doctorId;
	private String doctorName;
	private double consultantFee;

}
