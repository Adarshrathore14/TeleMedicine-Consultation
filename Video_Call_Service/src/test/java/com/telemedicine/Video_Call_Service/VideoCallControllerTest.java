package com.telemedicine.Video_Call_Service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.Video_Call_Service.controller.VideoCallController;
import com.telemedicine.Video_Call_Service.model.VideoConsultation;
import com.telemedicine.Video_Call_Service.service.VideoCallService;

@WebMvcTest(VideoCallController.class)
public class VideoCallControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VideoCallService videoCallService;

    @InjectMocks
    private VideoCallController videoCallController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(videoCallController).build();
    }

    @Test
    public void testGenerateMeetingLink() throws Exception {
        int appointmentId = 1;

        // Mock the behavior of the videoCallService
        when(videoCallService.generateMeetingLink(appointmentId)).thenReturn("https://goo.com/meeting/1");

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/videoCall/generateMeetingLink/{appointmentId}", appointmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("https://goo.com/meeting/1"));

        // Verify that the videoCallService method was called
        verify(videoCallService).generateMeetingLink(appointmentId);
    }

    @Test
    public void testGetVideoConsultation() throws Exception {
        int appointmentId = 1;

        // Mock the behavior of the videoCallService
        VideoConsultation videoConsultation = new VideoConsultation(); // Create a sample VideoConsultation
        when(videoCallService.getVideoConsultation(appointmentId)).thenReturn(videoConsultation);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/videoCall/getVideoConsultation/{appointmentId}", appointmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(videoConsultation))); // Adjust based on your VideoConsultation structure

        // Verify that the videoCallService method was called
        verify(videoCallService).getVideoConsultation(appointmentId);
    }
}
