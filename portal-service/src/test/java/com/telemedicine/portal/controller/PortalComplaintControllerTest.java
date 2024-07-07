package com.telemedicine.portal.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.portal.dto.PortalIssueDto;
import com.telemedicine.portal.entity.PortalIssueEntity;
import com.telemedicine.portal.service.PortalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
@ExtendWith(MockitoExtension.class)
class PortalComplaintControllerTest {
    @Mock
    private PortalService portalService;
    @Mock
    private SecurityContextHolder securityContextHolder;
    @InjectMocks
    private PortalComplaintController complaintController;
    private PortalIssueEntity portalComplaint;
    private PortalIssueEntity portalComplaint2;
    private PortalIssueEntity resolveComplaint;
    private PortalIssueDto complaintResponse;
    private PortalIssueDto resolveComplaintResponse;
    private ModelMapper modelMapper;
    private List<PortalIssueEntity> allComplaints;
    private List<PortalIssueDto> allComplaintsResponse;
    private MockMvc mockMvc;
    private String requestBody;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        objectMapper = new ObjectMapper();
        portalComplaint = new PortalIssueEntity("Tkt1","123","payment","not able to do Payment","active", null,null);
        resolveComplaint=new PortalIssueEntity("Tkt1","123","payment","not able to do Payment","resolved", null,null);
        resolveComplaintResponse = new PortalIssueDto();
        resolveComplaintResponse.setCategory(resolveComplaint.getCategory());
        resolveComplaintResponse.setComplaintStatus(resolveComplaint.getComplaintStatus());
        resolveComplaintResponse.setTicketNumber(resolveComplaint.getTicketNumber());
        resolveComplaintResponse.setPatientId(resolveComplaint.getPatientId());
        resolveComplaintResponse.setIssueDescription(resolveComplaint.getIssueDescription());
        complaintResponse = new PortalIssueDto();
        complaintResponse.setCategory(portalComplaint.getCategory());
        complaintResponse.setComplaintStatus(portalComplaint.getComplaintStatus());
        complaintResponse.setTicketNumber(portalComplaint.getTicketNumber());
        complaintResponse.setPatientId(portalComplaint.getPatientId());
        complaintResponse.setIssueDescription(portalComplaint.getIssueDescription());
        portalComplaint2 = new PortalIssueEntity("Tkt2","124","payment","not able to do Payment","active", LocalDateTime.now(),null);
        allComplaints = List.of(portalComplaint,portalComplaint2);
        allComplaintsResponse = allComplaints.stream().map(complaint->modelMapper.map(complaint, PortalIssueDto.class)).toList();
        this.mockMvc = MockMvcBuilders.standaloneSetup(complaintController)
                .build();
    }

    @Test
    void addComplaint() throws  Exception{
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("patient123");
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(portalService.addComplaint("patient123",portalComplaint)).thenReturn(complaintResponse);
        requestBody = objectMapper.writeValueAsString(portalComplaint);
        mockMvc.perform(MockMvcRequestBuilders.post("/complaint/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketNumber").value("Tkt1"));
    }

    @Test
    void getAllComplaints() throws Exception {
        Mockito.when(portalService.getAllComplaints()).thenReturn(allComplaintsResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/complaint/getAll"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    void resolveComplaint() throws Exception {
        Mockito.when(portalService.resolveComplaint("Tkt1")).thenReturn(resolveComplaintResponse);
        mockMvc.perform(MockMvcRequestBuilders.patch("/complaint/resolve/{ticketNumber}","Tkt1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));

    }
}