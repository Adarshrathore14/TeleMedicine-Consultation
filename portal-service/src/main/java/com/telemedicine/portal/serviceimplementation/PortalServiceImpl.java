package com.telemedicine.portal.serviceimplementation;
import com.telemedicine.portal.configurations.MessageConfiguration;
import com.telemedicine.portal.dto.PortalIssueDto;
import com.telemedicine.portal.entity.PortalIssueEntity;
import com.telemedicine.portal.exceptions.InvalidTicketNumberException;
import com.telemedicine.portal.exceptions.NoComplaintsException;
import com.telemedicine.portal.repository.PortalRepository;
import com.telemedicine.portal.service.PortalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @author Harish Babu
 * @version 0.0.1
 * <p> This service will take care of the following operations </p>
 * <p> Registering complaint</p>
 * <p> getting all complaint</p>
 * <p>resolving the complaints</p>
 */
@Service
@AllArgsConstructor
public class PortalServiceImpl implements PortalService {
    private final PortalRepository portalRepository;
    private final MessageConfiguration messageConfiguration;
    private final ModelMapper modelMapper;
    @Override
    public PortalIssueDto addComplaint(String patientId, PortalIssueEntity portalIssue) {
        portalIssue.setPatientId(patientId);
        portalIssue.setComplaintStatus(messageConfiguration.getActive());
        portalIssue.setCreatedDate(LocalDateTime.now());
        portalRepository.save(portalIssue);
        return modelMapper.map(portalIssue, PortalIssueDto.class);
    }


    @Override
    public List<PortalIssueDto> getAllComplaints() throws NoComplaintsException {
        List<PortalIssueEntity> portalComplaints = portalRepository.findAll();
        if(portalComplaints.isEmpty()){
            throw new NoComplaintsException("no complaints available");
        }
        return portalComplaints.stream().map(complaint ->modelMapper.map(complaint,
                PortalIssueDto.class)).toList();
    }

    @Override
    public PortalIssueDto resolveComplaint(String ticketNumber) throws InvalidTicketNumberException {
        PortalIssueEntity portalIssue = portalRepository.findByTicketNumber(ticketNumber).orElseThrow(
                ()->new InvalidTicketNumberException("ticket number is invalid"));
        portalIssue.setComplaintStatus(messageConfiguration.getResolved());
        portalIssue.setModifiedDate(LocalDateTime.now());
        portalRepository.save(portalIssue);
        return modelMapper.map(portalIssue, PortalIssueDto.class);
    }
}
