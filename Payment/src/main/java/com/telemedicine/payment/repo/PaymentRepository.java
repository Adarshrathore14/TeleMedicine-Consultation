package com.telemedicine.payment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedicine.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
