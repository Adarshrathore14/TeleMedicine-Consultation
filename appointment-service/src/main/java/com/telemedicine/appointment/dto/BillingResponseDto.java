package com.telemedicine.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingResponseDto {
	private int billNumber;
    private int appointmentId;
    private double consultationFees;
}
