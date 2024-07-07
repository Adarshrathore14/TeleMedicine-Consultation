package com.telemedicine.patient.feignresponse;
import com.telemedicine.patient.dto.PortalIssueDto;
import com.telemedicine.patient.entity.PortalIssueEntity;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "portal-service",path = "/portal/complaint")
public interface PortalFeignResponse {
    @PostMapping("/add")
    @CircuitBreaker(name = "Portal-Service",fallbackMethod = "portalFallBack")
    ResponseEntity<PortalIssueDto> addComplaint(@RequestBody PortalIssueEntity portalIssueEntity);
    default ResponseEntity<PortalIssueDto> portalFallBack(CallNotPermittedException exception) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new PortalIssueDto());
    }
}
