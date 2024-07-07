package com.telemedicine.billing.apidefinitions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.telemedicine.billing.dto.BillingResponseDto;

@Tag(name = "billing",description = "describes billing and some operations on billing table")
public interface BillingApiDefinition {
	
    
    @Operation(summary = "Get List of billings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning all the schedules"),
            @ApiResponse(responseCode = "404",description = "No schedules")
    })
    public ResponseEntity<List<BillingResponseDto>> getAllBillings() ;

    
    @Operation(summary = "get billing by appointmentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning the billings based on appointment id"),
            @ApiResponse(responseCode = "404",description = "There is no billing with given id")
    })
    public ResponseEntity<BillingResponseDto> getBillingByAppointmentId(@PathVariable int appointmentId);
}
