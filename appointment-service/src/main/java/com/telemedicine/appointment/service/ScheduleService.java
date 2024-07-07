package com.telemedicine.appointment.service;
import com.telemedicine.appointment.dto.ScheduleResponse;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.exceptions.NoSchedulesAvailableException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getAllSchedulesByDate(LocalDate date) throws NoSchedulesAvailableException;
    ScheduleResponse getSchedulesByDoctorId(int doctorId) throws NoSchedulesAvailableException,InvalidDoctorIdException;
    ScheduleResponse getSchedulesByDoctorIdAndDate(int doctorId, LocalDate availableDate) throws
            NoSchedulesAvailableException, InvalidDoctorIdException;
}
