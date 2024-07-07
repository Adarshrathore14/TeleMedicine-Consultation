package com.telemedicine.appointment.dto;
import lombok.Data;
@Data
public class AppointmentDto {// once saving into db we are streaming this into topic that will be consumed by the billing-service
    private int appointmentId;
    private double consultationFees;
}
