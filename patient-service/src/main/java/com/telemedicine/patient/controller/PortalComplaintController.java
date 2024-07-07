package com.telemedicine.patient.controller;

import com.telemedicine.patient.apidefinitions.PortalComplaintApiDefinition;
import com.telemedicine.patient.dto.PortalIssueDto;
import com.telemedicine.patient.entity.PortalIssueEntity;
import com.telemedicine.patient.exceptions.InvalidTicketNumberException;
import com.telemedicine.patient.exceptions.NoComplaintsAvailableException;
import com.telemedicine.patient.service.PortalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/complaint")
public class PortalComplaintController implements PortalComplaintApiDefinition {
    private final PortalService portalService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<PortalIssueDto> addComplaint(@Valid @RequestBody PortalIssueEntity portalIssueEntity){
        return portalService.addComplaint(portalIssueEntity);
    }
    @GetMapping("/getMyComplaints")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<List<PortalIssueDto>> getAllMyComplaints() throws NoComplaintsAvailableException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.ok(portalService.getAllMyComplaints(patientId));
    }
    @GetMapping("/complaintStatus/{ticketNumber}")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<PortalIssueDto> getMyComplaintStatus(@PathVariable String ticketNumber)
            throws InvalidTicketNumberException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.ok(portalService.getMyComplaintStatus(patientId,ticketNumber));
    }
}
