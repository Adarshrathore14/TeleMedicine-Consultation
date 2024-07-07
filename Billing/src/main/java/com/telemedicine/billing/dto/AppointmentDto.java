package com.telemedicine.billing.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
	private int appointmentId;
    private double consultationFees;
}
