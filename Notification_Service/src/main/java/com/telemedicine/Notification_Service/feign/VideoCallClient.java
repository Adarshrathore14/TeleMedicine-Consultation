package com.telemedicine.Notification_Service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.telemedicine.Notification_Service.entity.VideoConsultation;

@FeignClient("video-call")
public interface VideoCallClient {

//    @GetMapping("/videoCall/generateMeetingLink/{appointmentId}")
//    String generateMeetingLink(@PathVariable("appointmentId") String appointmentId);
	
	@PostMapping("/videoCall/generateMeetingLink/{appointmentId}")
	public ResponseEntity<String> generateMeetingLink(@PathVariable int appointmentId);

//    @GetMapping("/video-call/get-meeting-link/{appointId}")
//    String getMeetingLink(@PathVariable("appointId") Integer appointId);
    
//    @GetMapping("/videoCall/getVideoConsultation/{appointmentId}")
//    VideoConsultation getVideoConsultation(@PathVariable("appointmentId") String appointmentId);
    
    @GetMapping("/videoCall/getVideoConsultation/{appointmentId}")
    public ResponseEntity<VideoConsultation> getVideoConsultation(@PathVariable int appointmentId);
}
