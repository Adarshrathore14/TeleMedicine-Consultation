package com.telemedicine.Video_Call_Service.service;

import org.springframework.stereotype.Service;

import com.telemedicine.Video_Call_Service.model.VideoConsultation;


public interface VideoCallService {

	String generateMeetingLink(int appointmentId);

    VideoConsultation getVideoConsultation(int appointmentId);
}

