package com.telemedicine.admin.dto;


import java.time.LocalDate;
import java.util.List;

import com.telemedicine.admin.entity.SlotTiming;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int scheduleId;
	private DoctorDetailsResponse doctor;
	private List<SlotTiming> slotsAvailable;
	private LocalDate scheduleDate;
}