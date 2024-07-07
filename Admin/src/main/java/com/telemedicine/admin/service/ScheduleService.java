package com.telemedicine.admin.service;

import com.telemedicine.admin.dto.AppointmentFromAppointment;
import com.telemedicine.admin.dto.ScheduleResponse;
import com.telemedicine.admin.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getAllSchedules();

    ScheduleResponse getScheduleById(int id);

    ScheduleResponse saveSchedule(Schedule schedule);

    ScheduleResponse updateSchedule(int id, Schedule updatedSchedule);

    void deleteSchedule(int id);

	List<ScheduleResponse> getScheduleByDate(LocalDate date);

	List<ScheduleResponse> getSchedulesDoctorId(int id);

	void removeBookedSlot(AppointmentFromAppointment appointment);
}
