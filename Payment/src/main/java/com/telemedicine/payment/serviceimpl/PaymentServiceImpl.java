package com.telemedicine.payment.serviceimpl;

import org.springframework.stereotype.Service;

import com.telemedicine.payment.dto.AppointmentFromAppointment;
import com.telemedicine.payment.dto.NotificationResponseDto;
import com.telemedicine.payment.dto.PaymentRequest;
import com.telemedicine.payment.entity.Payment;
import com.telemedicine.payment.exception.ResourceNotFoundException;
import com.telemedicine.payment.external.AdminFeignClient;
import com.telemedicine.payment.external.AppointmentFeignClient;
import com.telemedicine.payment.external.BillingFeignClient;
import com.telemedicine.payment.external.DoctorFeignClients;
import com.telemedicine.payment.external.NotificationFeignClient;
import com.telemedicine.payment.repo.PaymentRepository;
import com.telemedicine.payment.service.PaymentService;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    
    private final BillingFeignClient billingFeignClient;
    
    private final NotificationFeignClient notificatioFeignClient;
    
    private final DoctorFeignClients doctorFeignClient;
    
    private final AppointmentFeignClient appointmentFeignClient;
    
    private final AdminFeignClient adminFeignClient;
    
    
    public PaymentServiceImpl(PaymentRepository paymentRepository,BillingFeignClient billingFeignClient,NotificationFeignClient notificatioFeignClient,DoctorFeignClients doctorFeignClient,AppointmentFeignClient appointmentFeignClient,AdminFeignClient adminFeignClient) {
        this.paymentRepository = paymentRepository;
        this.billingFeignClient=billingFeignClient;
        this.notificatioFeignClient=notificatioFeignClient;
        this.doctorFeignClient=doctorFeignClient;
        this.appointmentFeignClient=appointmentFeignClient;
        this.adminFeignClient=adminFeignClient;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(int paymentId, Payment payment) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new ResourceNotFoundException("Payment not found with ID: " + paymentId);
        }
        payment.setPaymentId(paymentId);
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(int paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new ResourceNotFoundException("Payment not found with ID: " + paymentId);
        }
        paymentRepository.deleteById(paymentId);
    }

	@Override
	@Transactional
	public NotificationResponseDto doPayment(int billingId, PaymentRequest paymentRequest) {
		double cost=billingFeignClient.getFeeByBid(billingId);
		Payment payment=getPaymentById(2);
		if(payment.getAccountNumber()==paymentRequest.getAccountNumber())
		{
			if(payment.getAccountBalance()>=cost)
			{
				payment.setAccountBalance(payment.getAccountBalance()-cost);
				paymentRepository.save(payment);
				Integer appointmentId=billingFeignClient.getAppointmentIdByBid(billingId).getBody();
				if(billingFeignClient.getStatusByBid(billingId).getBody().equals("Paid"))
					throw new ResourceNotFoundException("This Bill already paid");
				billingFeignClient.setStatusByBid(billingId);
				/*
				 * doctorFeignClient.addAppointmentToDoctor(appointmentId);
				 * AppointmentFromAppointment
				 * appoint=appointmentFeignClient.getAppointmentDetails(appointmentId).getBody()
				 * ; adminFeignClient.removeBookedSlot(appoint);
				 */
				return notificatioFeignClient.sendNotification(appointmentId).getBody();
			}
			else
			{
				throw new ResourceNotFoundException("Account balance is less than total cost");			
			}
		}
		throw new ResourceNotFoundException("please check your account number ");	
		
	}
}
