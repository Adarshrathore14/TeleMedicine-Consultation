package com.telemedicine.patient.feignresponse;
import com.telemedicine.patient.dto.ScheduleResponse;
import com.telemedicine.patient.entity.AppointmentDetailsEntity;
import com.telemedicine.patient.dto.AppointmentResponse;
import com.telemedicine.patient.exceptions.AppointmentAlreadyExistsException;
import com.telemedicine.patient.exceptions.InvalidDoctorIdException;
import com.telemedicine.patient.exceptions.InvalidSlotIdException;
import com.telemedicine.patient.exceptions.NoSchedulesAvailableException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "appointment-service",path = "/appointmentService")
public interface AppointmentFeignResponse {
    @PostMapping("/appointment/create/{doctorId}")
    @CircuitBreaker(name = "Appointment-Service",fallbackMethod = "appointmentFallBack")
    ResponseEntity<AppointmentResponse> addAppointment(@PathVariable int doctorId, @Valid
    @RequestBody AppointmentDetailsEntity appointmentDetails) throws InvalidDoctorIdException,AppointmentAlreadyExistsException,
            InvalidSlotIdException;
    @GetMapping("/doctorSchedules/date")
    @CircuitBreaker(name = "Appointment-Service",fallbackMethod = "schedulesFallBack")
    ResponseEntity<List<ScheduleResponse>> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws NoSchedulesAvailableException;
    @GetMapping("/doctorSchedules/{doctorId}")
    @CircuitBreaker(name = "Appointment-Service",fallbackMethod = "doctorScheduleFallBack")
    ResponseEntity<ScheduleResponse> getSchedulesByDoctorId(@PathVariable int doctorId, @RequestParam(required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws InvalidDoctorIdException,NoSchedulesAvailableException;
    default ResponseEntity<List<ScheduleResponse>> schedulesFallBack(CallNotPermittedException exception){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new ArrayList<>());
    }
    default ResponseEntity<ScheduleResponse> doctorScheduleFallBack(CallNotPermittedException exception){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new ScheduleResponse());
    }
    default ResponseEntity<AppointmentResponse> appointmentFallBack(CallNotPermittedException exception){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new AppointmentResponse());
    }

}
