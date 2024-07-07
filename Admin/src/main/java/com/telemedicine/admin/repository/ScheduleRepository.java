package com.telemedicine.admin.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedicine.admin.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	public List<Schedule> findByScheduleDate(LocalDate scheduleDate);
	public List<Schedule> findByDoctorId(int id);
	public Optional<Schedule> findByDoctorIdAndScheduleDate(int doctorId,LocalDate scheduleDate);
}
