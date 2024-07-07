package com.telemedicine.appointment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SlotTiming {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sid;
	private LocalTime startTime;
	private LocalTime endTime;
}
