package com.telemedicine.appointment.controller;
import com.telemedicine.appointment.apidefinitions.ScheduleApiDefinition;
import com.telemedicine.appointment.dto.ScheduleResponse;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.exceptions.NoSchedulesAvailableException;
import com.telemedicine.appointment.service.ScheduleService;
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
        return ResponseEntity.ok(scheduleService.getAllSchedulesByDate(date));
    }
    @GetMapping("/{doctorId}")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<ScheduleResponse> getSchedulesByDoctorId(@PathVariable int doctorId,@RequestParam(required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws InvalidDoctorIdException, NoSchedulesAvailableException {
        return date!=null ? ResponseEntity.ok(scheduleService.getSchedulesByDoctorIdAndDate(doctorId,date)):
                ResponseEntity.ok(scheduleService.getSchedulesByDoctorId(doctorId));
    }
}
