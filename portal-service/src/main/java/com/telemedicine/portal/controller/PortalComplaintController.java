package com.telemedicine.portal.controller;
import com.telemedicine.portal.apidefinitions.PortalComplaintApiDefinition;
import com.telemedicine.portal.dto.PortalIssueDto;
import com.telemedicine.portal.entity.PortalIssueEntity;
import com.telemedicine.portal.exceptions.InvalidTicketNumberException;
import com.telemedicine.portal.exceptions.NoComplaintsException;
import com.telemedicine.portal.service.PortalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.status(HttpStatus.CREATED).body(portalService.addComplaint(patientId,portalIssueEntity));
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('IT')")
    @Override
    public ResponseEntity<List<PortalIssueDto>> getAllComplaints() throws NoComplaintsException {
        return ResponseEntity.ok(portalService.getAllComplaints());
    }
    @PatchMapping("/resolve/{ticketNumber}")
    @PreAuthorize("hasAuthority('IT')")
    @Override
    public ResponseEntity<PortalIssueDto> resolveComplaint(@PathVariable String ticketNumber)
            throws InvalidTicketNumberException {
        return ResponseEntity.ok(portalService.resolveComplaint(ticketNumber));
    }
}
