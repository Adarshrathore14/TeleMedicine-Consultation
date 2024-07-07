package com.telemedicine.patient.dto;
import lombok.Data;

@Data
public class PortalIssueDto {
    private String ticketNumber;
    private String patientId;
    private String category;
    private String issueDescription;
    private String complaintStatus;
}
