package com.telemedicine.patient.service;

import com.telemedicine.patient.dto.ScheduleResponse;
import com.telemedicine.patient.exceptions.InvalidDoctorIdException;
import com.telemedicine.patient.exceptions.NoSchedulesAvailableException;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    ResponseEntity<List<ScheduleResponse>> getByDate(LocalDate date) throws NoSchedulesAvailableException;
    ResponseEntity<ScheduleResponse> getByDoctorId(int doctorId) throws InvalidDoctorIdException, NoSchedulesAvailableException;
    ResponseEntity<ScheduleResponse> getByDoctorIdAndDate(int doctorId, LocalDate date) throws InvalidDoctorIdException,
            NoSchedulesAvailableException;
}
