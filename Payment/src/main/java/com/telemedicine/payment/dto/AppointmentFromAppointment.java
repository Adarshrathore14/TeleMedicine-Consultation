package com.telemedicine.payment.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFromAppointment {
    
    private int appointmentId;
    private String patientId;//extract this from the Token
    private int slotId;
    private int doctorId;//this will be used to call the doctorService for getting the consultation fees.
    private LocalDate appointmentDate;
    private String paymentStatus;//to update the payment status
    private boolean notificationStatus;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedTime;
}
