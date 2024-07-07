package com.telemedicine.patient.serviceimplementation;
import com.telemedicine.patient.dto.PortalIssueDto;
import com.telemedicine.patient.entity.PortalIssueEntity;
import com.telemedicine.patient.exceptions.NoComplaintsAvailableException;
import com.telemedicine.patient.exceptions.InvalidTicketNumberException;
import com.telemedicine.patient.exceptions.ServiceUnavailableException;
import com.telemedicine.patient.feignresponse.PortalFeignResponse;
import com.telemedicine.patient.repository.PortalRepository;
import com.telemedicine.patient.service.PortalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
@Service
@AllArgsConstructor
public class PortalServiceImpl implements PortalService {
    private final PortalFeignResponse portalFeignResponse;
    private final PortalRepository portalRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<PortalIssueDto> addComplaint(PortalIssueEntity portalIssueEntity) {
        return portalFeignResponse.addComplaint(portalIssueEntity);
    }

    @Override
    public PortalIssueDto getMyComplaintStatus(String patientId, String ticketNumber) throws InvalidTicketNumberException {
        PortalIssueEntity portalIssue = portalRepository.findByTicketNumberAndPatientId(ticketNumber,patientId).orElseThrow(
                ()->new InvalidTicketNumberException("ticket number is invalid"));
        return modelMapper.map(portalIssue, PortalIssueDto.class);
    }

    @Override
    public List<PortalIssueDto> getAllMyComplaints(String patientId) throws NoComplaintsAvailableException {
        List<PortalIssueEntity> portalComplaints = portalRepository.findAllByPatientId(patientId).orElseThrow(
                ()->new NoComplaintsAvailableException("no complaints found"));
        return portalComplaints.stream().map(portalIssueEntity -> modelMapper.map(portalIssueEntity, PortalIssueDto.class))
                .toList();
    }
}

