package com.telemedicine.portal.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
/**
 * @author Harish Babu
 * @version 0.0.1
 * <p> The parameters required to maintain the record of complaints in the mysql database </p>
 */
public class PortalIssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "ticketNumber")
    @GenericGenerator(name="ticketNumber",type = TicketNumberGenerator.class)
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
