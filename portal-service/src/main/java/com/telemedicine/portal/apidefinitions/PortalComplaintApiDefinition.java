package com.telemedicine.portal.apidefinitions;
import com.telemedicine.portal.dto.PortalIssueDto;
import com.telemedicine.portal.entity.PortalIssueEntity;
import com.telemedicine.portal.exceptions.InvalidTicketNumberException;
import com.telemedicine.portal.exceptions.NoComplaintsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
@Tag(name = "portalComplaint",description = "Handles the issues related to portal")
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
            @ApiResponse(responseCode = "204",description = "no complaints available")
    })
    ResponseEntity<List<PortalIssueDto>> getAllComplaints() throws NoComplaintsException;
    @Operation(summary = "Resolves the complaint raised by the user by the IT team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "resolved"),
            @ApiResponse(responseCode = "404",description = "ticket number is valid")
    })
    ResponseEntity<PortalIssueDto> resolveComplaint(@PathVariable String ticketNumber)
            throws InvalidTicketNumberException;
}
