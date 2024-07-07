package com.telemedicine.portal.repository;
import com.telemedicine.portal.entity.PortalIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PortalRepository extends JpaRepository<PortalIssueEntity,String> {
    Optional<PortalIssueEntity> findByTicketNumber(String ticketNumber);
}
