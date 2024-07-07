package com.telemedicine.Video_Call_Service.controller;

import com.telemedicine.Video_Call_Service.model.VideoConsultation;
import com.telemedicine.Video_Call_Service.service.VideoCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videoCall")
public class VideoCallController {

    @Autowired
    private VideoCallService videoCallService;
    

    @PostMapping("/generateMeetingLink/{appointmentId}")
    public ResponseEntity<String> generateMeetingLink(@PathVariable int appointmentId) {
        return new ResponseEntity<String>(videoCallService.generateMeetingLink(appointmentId),HttpStatus.CREATED);
    }

    @GetMapping("/getVideoConsultation/{appointmentId}")
    public ResponseEntity<VideoConsultation> getVideoConsultation(@PathVariable int appointmentId) {
        return  new ResponseEntity<VideoConsultation>(videoCallService.getVideoConsultation(appointmentId),HttpStatus.OK);
    }
    
    
 // Fallback method for generateMeetingLink
    private ResponseEntity<String> fallbackGenerateMeetingLink(int appointmentId, Throwable throwable) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit breaker triggered for generateMeetingLink");
    }

    // Fallback method for getVideoConsultation
    private ResponseEntity<VideoConsultation> fallbackGetVideoConsultation(int appointmentId, Throwable throwable) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    
}
