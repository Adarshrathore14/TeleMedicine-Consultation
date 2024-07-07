package com.telemedicine.billing.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponseDto {
	private int billNumber;
    private int appointmentId;
    private String status;
    private double consultationFees;
}
