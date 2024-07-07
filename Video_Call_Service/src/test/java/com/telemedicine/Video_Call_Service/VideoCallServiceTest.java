package com.telemedicine.Video_Call_Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.telemedicine.Video_Call_Service.model.VideoConsultation;
import com.telemedicine.Video_Call_Service.repository.VideoConsultationRepository;
import com.telemedicine.Video_Call_Service.serviceImpl.VideoCallServiceImpl;


@ExtendWith(MockitoExtension.class)
public class VideoCallServiceTest {
	
	@Mock
    private VideoConsultationRepository videoConsultationRepository;

    @InjectMocks
    private VideoCallServiceImpl videoCallService;

    @Test
    public void testGenerateMeetingLink() {
        int appointmentId = 1;
        
        // Mock the behavior of the repository save method
        when(videoConsultationRepository.save(any(VideoConsultation.class))).thenAnswer(invocation -> {
            VideoConsultation savedVideoConsultation = invocation.getArgument(0);
            savedVideoConsultation.setAppointmentId(1); // Simulate the save operation setting the ID
            return savedVideoConsultation;
        });

        // Call the method to be tested
        String meetingLink = videoCallService.generateMeetingLink(appointmentId);

        // Verify that the repository save method was called
        verify(videoConsultationRepository).save(any(VideoConsultation.class));

        // Verify that the returned meeting link is not null and has the expected format
        assertNotNull(meetingLink);
        assertTrue(meetingLink.startsWith("https://goo.com/meeting/"));
    }

    @Test
    public void testGetVideoConsultation() {
        int appointmentId = 1;

        // Mock the behavior of the repository findByAppointmentId method
        when(videoConsultationRepository.findByAppointmentId(appointmentId)).thenReturn(Optional.of(new VideoConsultation()));

        // Call the method to be tested
        VideoConsultation videoConsultation = videoCallService.getVideoConsultation(appointmentId);

        // Verify that the repository findByAppointmentId method was called
        verify(videoConsultationRepository).findByAppointmentId(appointmentId);

        // Verify that the returned VideoConsultation is not null
        assertNotNull(videoConsultation);
    }
	
	

}
