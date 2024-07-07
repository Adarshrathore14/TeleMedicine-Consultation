package com.telemedicine.patient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotTiming {
	private int sid;
	private LocalTime startTime;
	private LocalTime endTime;
}
