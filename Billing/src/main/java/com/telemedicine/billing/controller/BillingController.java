package com.telemedicine.billing.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.telemedicine.billing.apidefinitions.BillingApiDefinition;
import com.telemedicine.billing.dto.BillingResponseDto;
import com.telemedicine.billing.service.BillingService;

import java.util.List;
@RestController
@RequestMapping("/billing")
public class BillingController implements BillingApiDefinition{

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public ResponseEntity<List<BillingResponseDto>> getAllBillings() {
    	List<BillingResponseDto> billings=billingService.getAllBillings();
    	if (!billings.isEmpty()) {
            return new ResponseEntity<>(billings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<BillingResponseDto> getBillingByAppointmentId(@PathVariable int appointmentId) {
    	BillingResponseDto billing = billingService.getBillingByAppointmentId(appointmentId);
        return new ResponseEntity<>(billing, HttpStatus.OK);
    }
    
	@GetMapping("/getfee/{bid}")
    public ResponseEntity<Double> getFeeByBid(@PathVariable int bid)
    {
		if(billingService.getBillingById(bid)!=null)
		{
			return new ResponseEntity<>(billingService.getBillingById(bid).getConsultationFees(),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
	@GetMapping("/getappointmentid/{bid}")
	public ResponseEntity<Integer> getAppointmentIdByBid(@PathVariable int bid)
	{
		if(billingService.getBillingById(bid)!=null)
		{
			return new ResponseEntity<>(billingService.getBillingById(bid).getAppointmentId(),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/getstatus/{bid}")
	public ResponseEntity<String> getStatusByBid(@PathVariable int bid)
	{
		if(billingService.getBillingById(bid)!=null)
		{
			return new ResponseEntity<>(billingService.getBillingById(bid).getStatus(),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/setstatus/{bid}")
	public void setStatusByBid(@PathVariable int bid)
	{
		billingService.setStatusByBid(bid);
	}
}
