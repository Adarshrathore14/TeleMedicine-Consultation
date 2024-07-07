package com.telemedicine.patient.apidefinitions;
import com.telemedicine.patient.dto.MedicalRecordResponse;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import java.util.List;
@Tag(name = "MedicalRecordApi-Definition",description = "provides the List of medicalRecords")
public interface MedicalRecordApiDefinition {
    @Operation(summary = "get All Medical Records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "complaint raised successfully")
    })
    ResponseEntity<List<MedicalRecordResponse>> getDoctorPrescriptions() throws InvalidPatientIdException;
}
