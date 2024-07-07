package com.telemedicine.patient.apidefinitions;

import com.telemedicine.patient.dto.PortalIssueDto;
import com.telemedicine.patient.entity.PortalIssueEntity;
import com.telemedicine.patient.exceptions.InvalidTicketNumberException;
import com.telemedicine.patient.exceptions.NoComplaintsAvailableException;
import com.telemedicine.patient.exceptions.ServiceUnavailableException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Tag(name = "Patient Service Portal Complaint",description = "Handles the issues related to portal")
public interface PortalComplaintApiDefinition {
    @Operation(summary = "Adds complaint related to portal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "complaint raised successfully"),
            @ApiResponse(responseCode = "406",description = "validation errors")
    })
    ResponseEntity<PortalIssueDto> addComplaint(@Valid @RequestBody PortalIssueEntity portalIssueEntity);
    @Operation(summary = "Gets all Complaints related to portal raised by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "gets the list of complaints"),
            @ApiResponse(responseCode = "204",description = "No Complaints Raised")
    })
    ResponseEntity<List<PortalIssueDto>> getAllMyComplaints() throws NoComplaintsAvailableException;
    @Operation(summary = "Resolves the complaint raised by the user by the IT team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "resolved"),
            @ApiResponse(responseCode = "404",description = "ticket number is valid")
    })
    ResponseEntity<PortalIssueDto> getMyComplaintStatus(@PathVariable String ticketNumber)
            throws InvalidTicketNumberException;
}
