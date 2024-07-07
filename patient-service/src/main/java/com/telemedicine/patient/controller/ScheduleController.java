package com.telemedicine.patient.controller;
import com.telemedicine.patient.apidefinitions.ScheduleApiDefinition;
import com.telemedicine.patient.dto.ScheduleResponse;
import com.telemedicine.patient.exceptions.InvalidDoctorIdException;
import com.telemedicine.patient.exceptions.NoSchedulesAvailableException;
import com.telemedicine.patient.exceptions.ServiceUnavailableException;
import com.telemedicine.patient.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/doctorSchedules")
public class ScheduleController implements ScheduleApiDefinition {
    private final ScheduleService scheduleService;
    @GetMapping("/date")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<List<ScheduleResponse>> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws NoSchedulesAvailableException {
        return scheduleService.getByDate(date);
    }
    @GetMapping("/{doctorId}")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<ScheduleResponse> getSchedulesByDoctorId(@PathVariable int doctorId,@RequestParam(required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws InvalidDoctorIdException, NoSchedulesAvailableException, ServiceUnavailableException {
        return date!=null ?scheduleService.getByDoctorIdAndDate(doctorId,date):scheduleService.getByDoctorId(doctorId);
    }
}
