package com.telemedicine.billing.service;
import java.util.List;

import com.telemedicine.billing.dto.BillingResponseDto;
import com.telemedicine.billing.entity.Billing;
public interface BillingService {
    List<BillingResponseDto> getAllBillings();
    BillingResponseDto getBillingByAppointmentId(int appointmentId);
    Billing getBillingById(int id);
	void setStatusByBid(int bid);
}
