package com.telemedicine.DoctorService.model;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doctorId;
	private String doctorName;
	private double consultantFee;
	@OneToMany(targetEntity = AppointmentDetailsEntity.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "doctorId",referencedColumnName = "doctorId")
	private List<AppointmentDetailsEntity> patientAppointments;

}
