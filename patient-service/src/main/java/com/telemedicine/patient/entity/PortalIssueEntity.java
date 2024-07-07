package com.telemedicine.patient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PortalIssueEntity {
    @Id
    private String ticketNumber;
    private String patientId;
    @NotBlank(message = "category is required")
    private String category;
    @NotBlank(message = "description is required")
    private String issueDescription;
    private String complaintStatus;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
