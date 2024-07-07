package com.telemedicine.patient.service;
import com.telemedicine.patient.dto.AppointmentDetails;
import com.telemedicine.patient.dto.AppointmentResponse;
import com.telemedicine.patient.entity.AppointmentDetailsEntity;
import com.telemedicine.patient.exceptions.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface AppointmentService {
    ResponseEntity<AppointmentResponse> createAppointment(int doctorId,AppointmentDetailsEntity appointmentDetails) throws
            InvalidDoctorIdException, AppointmentAlreadyExistsException,ServiceUnavailableException,
            InvalidSlotIdException;
    List<AppointmentDetails> myAppointments(String patientId) throws NoAppointmentsAvailableException;
}
