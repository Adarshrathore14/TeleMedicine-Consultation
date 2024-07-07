package com.telemedicine.patient.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PatientEntity {
    @Id
    private String patientId;
    private String patientName;
    private String userName;
    private String email;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String password;
    @OneToMany(targetEntity = AppointmentDetailsEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "patientId",referencedColumnName = "patientId")
    private List<AppointmentDetailsEntity> appointments;
    @OneToMany(targetEntity = PortalIssueEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "patientId",referencedColumnName = "patientId")
    private List<PortalIssueEntity> portalComplaints;
    @OneToMany(targetEntity = MedicalRecordEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "patientId",referencedColumnName = "patientId")
    private List<MedicalRecordEntity> medicalRecordEntity;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
