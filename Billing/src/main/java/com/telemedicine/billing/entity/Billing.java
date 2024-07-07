package com.telemedicine.billing.entity;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Billing {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int billNumber;
	private int appointmentId;
	private String status;
    private LocalDate billingDate;
    private double consultationFees;
}
