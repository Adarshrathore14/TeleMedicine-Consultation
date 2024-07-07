package com.telemedicine.patient.repository;
import com.telemedicine.patient.entity.PortalIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PortalRepository extends JpaRepository<PortalIssueEntity,String> {
    Optional<PortalIssueEntity> findByTicketNumber(String ticketNumber);
    Optional<PortalIssueEntity> findByTicketNumberAndPatientId(String ticketNumber,String patientId);
    Optional<List<PortalIssueEntity>> findAllByPatientId(String patientId);
}
