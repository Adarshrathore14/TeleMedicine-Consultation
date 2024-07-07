package com.telemedicine.admin.apidefinitions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.telemedicine.admin.entity.SlotTiming;

@Tag(name = "slot timings",description = "describes slots and basic crud operations on slots")
public interface SlotTimingApiDefinition {
	
    @Operation(summary = "Adds an slot by the admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "slot created successfully")
    })
    public ResponseEntity<SlotTiming> saveSlotTiming(@RequestBody SlotTiming slotTiming);
    
    @Operation(summary = "Get slot by schedule Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning the slot  by slot id"),
            @ApiResponse(responseCode = "404",description = "There is no slot with given slot id")
    })
    public ResponseEntity<SlotTiming> getSlotTimingById(@PathVariable int id);
    
    @Operation(summary = "Get List of slots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning all the slots"),
            @ApiResponse(responseCode = "404",description = "No slots")
    })
    public ResponseEntity<List<SlotTiming>> getAllSlotTimings();
    
    @Operation(summary = "Update slot by slot id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "updating the slot by slot id"),
            @ApiResponse(responseCode = "404",description = "There is no slot with given slot id")
    })
    public ResponseEntity<SlotTiming> updateSlotTiming(@PathVariable int id, @RequestBody SlotTiming updatedSlotTiming) ;
    
    @Operation(summary = "Delete slot by slot id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deleting the slot by slot id"),
            @ApiResponse(responseCode = "404",description = "There is no slot with given slot id")
    })
    public ResponseEntity<Void> deleteSlotTiming(@PathVariable int id);
}
