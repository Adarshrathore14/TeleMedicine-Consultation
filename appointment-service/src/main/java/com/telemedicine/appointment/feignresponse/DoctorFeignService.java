package com.telemedicine.appointment.feignresponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "doctor-Service",path = "/doctor")
public interface DoctorFeignService {
    @GetMapping("/getconsultantfeebydoctorid/{doctorId}")
    @CircuitBreaker(name="Doctor-Service",fallbackMethod = "doctorFallBack")
    double getConsultantFeeByDoctorId(@PathVariable int doctorId);
    default  double doctorFallBack(CallNotPermittedException exception){
        return -1.00;
    }
}
