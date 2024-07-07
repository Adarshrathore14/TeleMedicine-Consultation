package com.telemedicine.patient.service;
import com.telemedicine.patient.dto.PortalIssueDto;
import com.telemedicine.patient.entity.PortalIssueEntity;
import com.telemedicine.patient.exceptions.NoComplaintsAvailableException;
import com.telemedicine.patient.exceptions.InvalidTicketNumberException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PortalService {
    ResponseEntity<PortalIssueDto> addComplaint(PortalIssueEntity portalIssueEntity);
    PortalIssueDto getMyComplaintStatus(String patientId,String ticketNumber) throws InvalidTicketNumberException;
    List<PortalIssueDto> getAllMyComplaints(String patientId) throws NoComplaintsAvailableException;
}
