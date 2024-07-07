package com.telemedicine.Video_Call_Service.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telemedicine.Video_Call_Service.exception.MeetingLinkGenerationException;
import com.telemedicine.Video_Call_Service.exception.VideoConsultationNotFoundException;
import com.telemedicine.Video_Call_Service.model.Appointment;
import com.telemedicine.Video_Call_Service.model.VideoConsultation;
import com.telemedicine.Video_Call_Service.repository.VideoConsultationRepository;
import com.telemedicine.Video_Call_Service.service.VideoCallService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class VideoCallServiceImpl implements VideoCallService {

    
    private final VideoConsultationRepository videoConsultationRepository;

    @Override
    public String generateMeetingLink(int appointmentId) {
        System.out.println(appointmentId);
    	if(appointmentId>0) {
            // Logic to generate meeting link
            String meetingLink = "https://goo.com/meeting/" + appointmentId;

            // Save meeting link in the database
            VideoConsultation videoConsultation = new VideoConsultation();
           // Appointment appoint = new Appointment(1,"app1",1011.2d,3,"pat1",LocalDate.now());
            videoConsultation.setAppointmentId(appointmentId);
            videoConsultation.setMeetingLink(meetingLink);
            videoConsultationRepository.save(videoConsultation);
            return meetingLink;
            }
    	
    		throw new MeetingLinkGenerationException("Meeting link can not be null");
    	
        
            
    }

    @Override
    public VideoConsultation getVideoConsultation(int appointmentId) {
        
            return videoConsultationRepository.findByAppointmentId(appointmentId).orElseThrow(()-> new VideoConsultationNotFoundException("No appointment booked with this Id"));
                    
       
    }
}