package com.telemedicine.patient.feignresponse;
import java.util.ArrayList;
import java.util.List;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.telemedicine.patient.dto.DoctorResponse;
@FeignClient(name = "doctor-Service",path = "/doctor")
public interface DoctorServiceFeignClient {

    @GetMapping("/getalldoctors")
    @CircuitBreaker(name = "Doctor-Service",fallbackMethod = "defaultAllDoctorsFallBack")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors();

    @GetMapping("/getdoctorbyid/{doctorId}")
    @CircuitBreaker(name = "Doctor-Service",fallbackMethod = "defaultProfileFallBack")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable int doctorId);
    default ResponseEntity<List<DoctorResponse>> defaultAllDoctorsFallBack(CallNotPermittedException exception){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new ArrayList<>());
    }
    default ResponseEntity<DoctorResponse>defaultProfileFallBack(CallNotPermittedException exception){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new DoctorResponse());
    }
}
 
