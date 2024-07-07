package com.telemedicine.payment.service;

import java.util.List;

import com.telemedicine.payment.dto.NotificationResponseDto;
import com.telemedicine.payment.dto.PaymentRequest;
import com.telemedicine.payment.entity.Payment;

public interface PaymentService {
    List<Payment> getAllPayments();
    Payment getPaymentById(int paymentId);
    Payment savePayment(Payment payment);
    Payment updatePayment(int paymentId, Payment payment);
    void deletePayment(int paymentId);
    NotificationResponseDto doPayment(int billingId, PaymentRequest paymentRequest);
}
