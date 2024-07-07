package com.telemedicine.payment.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponseDto {
	private int billingId;
	private AppointmentDto appointmentId;
	private double consultantFee;

}
