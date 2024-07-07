package com.telemedicine.portal.service;
import com.telemedicine.portal.dto.PortalIssueDto;
import com.telemedicine.portal.entity.PortalIssueEntity;
import com.telemedicine.portal.exceptions.InvalidTicketNumberException;
import com.telemedicine.portal.exceptions.NoComplaintsException;
import java.util.List;

public interface PortalService {
    PortalIssueDto addComplaint(String patientId,PortalIssueEntity portalIssue);
    List<PortalIssueDto> getAllComplaints() throws NoComplaintsException;
    PortalIssueDto resolveComplaint(String ticketNumber) throws InvalidTicketNumberException;

}
