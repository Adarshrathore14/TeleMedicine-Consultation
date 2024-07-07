package com.telemedicine.billing.serviceimpl;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.telemedicine.billing.dto.AppointmentDto;
import com.telemedicine.billing.dto.BillingResponseDto;
import com.telemedicine.billing.entity.Billing;
import com.telemedicine.billing.exception.ResourceNotFoundException;
import com.telemedicine.billing.repo.BillingRepository;
import com.telemedicine.billing.service.BillingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    
    private final ModelMapper modelMapper;

    public BillingServiceImpl(BillingRepository billingRepository,ModelMapper modelMapper) {
        this.billingRepository = billingRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public Billing getBillingById(int id)
    {
    	return billingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("There is no billing with given billing id:"+id));
    }

    @Override
    public List<BillingResponseDto> getAllBillings() {
    	List<BillingResponseDto> billingResponseList=new ArrayList<>();
    	List<Billing> billingList=billingRepository.findAll();
    	for(Billing billing:billingList)
    	{
    		billingResponseList.add(convertBillingToBillingResponse(billing));
    	}
        return billingResponseList;
    }

    @Override
    public BillingResponseDto getBillingByAppointmentId(int appointmentId) {
        Billing billing=billingRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing not found with ID: " + appointmentId));
        return convertBillingToBillingResponse(billing);
    }

    public  BillingResponseDto convertBillingToBillingResponse(Billing billing)
    {
    		return this.modelMapper.map(billing,BillingResponseDto.class);
    }
    @KafkaListener(topics = "appointment",groupId = "test")
	public void consume(AppointmentDto appointmentDto) {
    	if(appointmentDto!=null) {
    	Billing billing = new Billing();
    	billing.setAppointmentId(appointmentDto.getAppointmentId());
    	billing.setConsultationFees(appointmentDto.getConsultationFees());
    	billing.setBillingDate(LocalDate.now());
    	billing.setStatus("Pending");
    	billingRepository.save(billing);
    	}
    }
	@Override
	public void setStatusByBid(int bid) {
		Billing billing=getBillingById(bid);
		billing.setStatus("Paid");
		billingRepository.save(billing);
	}
}
