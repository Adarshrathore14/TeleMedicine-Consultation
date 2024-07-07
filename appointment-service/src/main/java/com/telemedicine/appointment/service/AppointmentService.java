package com.telemedicine.appointment.service;
import java.util.List;

import com.telemedicine.appointment.dto.NotificationResponse;

import com.telemedicine.appointment.dto.AppointmentResponse;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import com.telemedicine.appointment.exceptions.*;

public interface AppointmentService {
    AppointmentResponse createAppointment(int doctorId,String patientId, AppointmentDetailsEntity appointmentDetails) throws
            InvalidDoctorIdException, ServiceUnavailableException, AppointmentAlreadyExistsException,
            InvalidSlotIdException;
    NotificationResponse getAppointmentDetails(int appointmentId) throws InvalidAppointmentIdException;
//	List<AppointmentDetailsEntity> getAllAppointments();
}
