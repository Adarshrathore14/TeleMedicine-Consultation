package com.telemedicine.admin.entity;


import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Schedule {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int scheduleId;
	private int doctorId;
	private List<Integer> slotsAvailable;
	private LocalDate scheduleDate;
}