package com.telemedicine.billing.repo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedicine.billing.entity.Billing;

public interface BillingRepository extends JpaRepository<Billing, Integer> {
	Optional<Billing> findByAppointmentId(int appointmentNumber);
}
