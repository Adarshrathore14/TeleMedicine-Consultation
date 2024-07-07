package com.telemedicine.admin.controller;

import com.telemedicine.admin.apidefinitions.ScheduleApiDefinition;
import com.telemedicine.admin.dto.AppointmentFromAppointment;
import com.telemedicine.admin.dto.DateRequest;
import com.telemedicine.admin.dto.ScheduleResponse;
import com.telemedicine.admin.entity.Schedule;
import com.telemedicine.admin.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/schedules")
public class ScheduleController implements ScheduleApiDefinition{

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<ScheduleResponse>> getAllSchedules() {
    	List<ScheduleResponse> schedules=scheduleService.getAllSchedules();
    	if (!schedules.isEmpty()) {
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable int id) {
    	ScheduleResponse scheduleResponse = scheduleService.getScheduleById(id);
        if (scheduleResponse != null) {
            return new ResponseEntity<>(scheduleResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getbydate")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<ScheduleResponse>> getScheduleByDate(@RequestBody DateRequest dateRequest) {
    	List<ScheduleResponse> schedule = scheduleService.getScheduleByDate(dateRequest.getDate());
        if (!schedule.isEmpty()) {
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getbydoctorid/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<ScheduleResponse>> getSchedulesDoctorId(@PathVariable int id) {
    	List<ScheduleResponse> schedule = scheduleService.getSchedulesDoctorId(id);
        if (!schedule.isEmpty()) {
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ScheduleResponse> saveSchedule(@RequestBody Schedule schedule) {
    	ScheduleResponse savedSchedule = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable int id, @RequestBody Schedule updatedSchedule) {
    	ScheduleResponse schedule = scheduleService.updateSchedule(id, updatedSchedule);
        if (schedule != null) {
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/removeslot")
    public void removeBookedSlot(@RequestBody AppointmentFromAppointment appointment)
    {
    	scheduleService.removeBookedSlot(appointment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
