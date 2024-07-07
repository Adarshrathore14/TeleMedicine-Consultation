package com.telemedicine.Notification_Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.telemedicine.Notification_Service.entity.Notification;
import com.telemedicine.Notification_Service.entity.dto.NotificationResponseDto;
import com.telemedicine.Notification_Service.feign.AppointmentServiceClient;
import com.telemedicine.Notification_Service.feign.VideoCallClient;
import com.telemedicine.Notification_Service.repository.NotificationRepository;
import com.telemedicine.Notification_Service.service.NotificationService;

public class NotificationServiceTest {

	  @Autowired
	    private NotificationService notificationService;

	    @MockBean
	    private NotificationRepository notificationRepository;

	    @MockBean
	    private VideoCallClient videoCallClient;

	    @MockBean
	    private AppointmentServiceClient appointmentServiceClient;

	    @Test
	    public void testSendNotification() {
	        // Test data
	        int appointmentId = 1;

	        // Mock the behavior of the videoCallClient
	        when(videoCallClient.generateMeetingLink(appointmentId)).thenReturn(ResponseEntity.ok("https://goo.com/meeting/1"));
 
	        // Mock the behavior of the appointmentServiceClient
	        doNothing().when(appointmentServiceClient).updateRecord(appointmentId);

	        // Mock the behavior of the notificationRepository
	        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
	            Notification savedNotification = invocation.getArgument(0);
	            savedNotification.setAppointmentId(1); // Simulate the save operation setting the ID
	            return savedNotification;
	        });

	        // Call the method to be tested
	        NotificationResponseDto notificationResponseDto = notificationService.sendNotification(appointmentId);

	        // Assertions
	        assertNotNull(notificationResponseDto);
	        assertEquals("https://goo.com/meeting/1", notificationResponseDto.getMeetingLink());

	        // Verify that the videoCallClient and appointmentServiceClient methods were called
	        verify(videoCallClient).generateMeetingLink(appointmentId);
	        verify(appointmentServiceClient).updateRecord(appointmentId);

	        // Verify that the notificationRepository save method was called
	        verify(notificationRepository).save(any(Notification.class));
	    }
	
	
	
	
	
	
	

    @Test
    public void testGetNotificationsForDoctor() {
        // Mocks
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        VideoCallClient videoCallClient = mock(VideoCallClient.class);
        AppointmentServiceClient appointmentServiceClient = mock(AppointmentServiceClient.class);

        // Service
        NotificationService notificationService = new NotificationService(notificationRepository, videoCallClient, appointmentServiceClient);

        // Test data
        int doctorId = 1;

        // Mock the behavior of the notificationRepository
        when(notificationRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(Arrays.asList(new Notification())));

        // Call the method to be tested
        List<NotificationResponseDto> notifications = notificationService.getNotifications(doctorId);

        // Assertions
        assertNotNull(notifications);

        // Verify that the notificationRepository findByDoctorId method was called
        verify(notificationRepository).findByDoctorId(doctorId);
    }

    @Test
    public void testGetNotificationsForPatient() {
        // Mocks
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        VideoCallClient videoCallClient = mock(VideoCallClient.class);
        AppointmentServiceClient appointmentServiceClient = mock(AppointmentServiceClient.class);

        // Service
        NotificationService notificationService = new NotificationService(notificationRepository, videoCallClient, appointmentServiceClient);

        // Test data
        String patientId = "patient1";

        // Mock the behavior of the notificationRepository
        when(notificationRepository.findByPatientId(patientId)).thenReturn(Optional.of(Arrays.asList(new Notification())));

        // Call the method to be tested
        List<NotificationResponseDto> notifications = notificationService.getNotifications(patientId);

        // Assertions
        assertNotNull(notifications);

        // Verify that the notificationRepository findByPatientId method was called
        verify(notificationRepository).findByPatientId(patientId);
    }
}
