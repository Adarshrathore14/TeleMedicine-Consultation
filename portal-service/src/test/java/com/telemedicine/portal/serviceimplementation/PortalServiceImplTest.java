package com.telemedicine.portal.serviceimplementation;
import com.telemedicine.portal.configurations.MessageConfiguration;
import com.telemedicine.portal.dto.PortalIssueDto;
import com.telemedicine.portal.entity.PortalIssueEntity;
import com.telemedicine.portal.exceptions.InvalidTicketNumberException;
import com.telemedicine.portal.exceptions.NoComplaintsException;
import com.telemedicine.portal.repository.PortalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PortalServiceImplTest {
    @Mock
    private PortalRepository portalRepository;
    @Mock
    private MessageConfiguration messageConfiguration;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PortalServiceImpl portalService;
    private PortalIssueEntity portalComplaint;
    private PortalIssueEntity portalComplaint2;
    private PortalIssueEntity resolveComplaint;
    private PortalIssueDto resolveComplaintResponse;
    private PortalIssueDto complaintResponse;
    private List<PortalIssueEntity> allComplaints;
    private List<PortalIssueDto> allComplaintsResponse;

    @BeforeEach
    void setUp() {
        portalComplaint = new PortalIssueEntity("Tkt1","123","payment","not able to do Payment","active", LocalDateTime.now(),null);
        resolveComplaint=new PortalIssueEntity("Tkt1","123","payment","not able to do Payment","resolved", LocalDateTime.now(),LocalDateTime.now());
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
    }

    @Test
    void addComplaint() {
        Mockito.when(portalRepository.save(portalComplaint)).thenReturn(portalComplaint);
        Mockito.when(modelMapper.map(portalComplaint, PortalIssueDto.class)).thenReturn(complaintResponse);
        assertEquals("payment",portalService.addComplaint("123",portalComplaint).getCategory());
    }

    @Test
    void getAllComplaintsPositiveCase() throws NoComplaintsException {
        Mockito.when(portalRepository.findAll()).thenReturn(allComplaints);
        assertEquals(2,portalService.getAllComplaints().size());
    }
    @Test
    void getAllComplaintsNegativeCase() throws NoComplaintsException {
        Mockito.when(portalRepository.findAll()).thenReturn(new ArrayList<>());
        NoComplaintsException exception = assertThrows(NoComplaintsException.class,()->portalService.getAllComplaints());
        assertEquals("no complaints available",exception.getMessage());
    }
    @Test
    void resolveComplaintPositiveCase() throws InvalidTicketNumberException {
        Mockito.when(portalRepository.findByTicketNumber("123")).thenReturn(Optional.of(portalComplaint));
        Mockito.when(portalRepository.save(portalComplaint)).thenReturn(portalComplaint);
        Mockito.when(modelMapper.map(portalComplaint, PortalIssueDto.class)).thenReturn(resolveComplaintResponse);
        assertEquals("resolved",portalService.resolveComplaint("123").getComplaintStatus());
    }
    @Test
    void resolveComplaintNegativeCase() throws InvalidTicketNumberException {
        Mockito.when(portalRepository.findByTicketNumber("123")).thenReturn(Optional.empty());
        InvalidTicketNumberException exception = assertThrows(InvalidTicketNumberException.class,()->portalService.resolveComplaint("123"));
        assertEquals("ticket number is invalid",exception.getMessage());
    }
}