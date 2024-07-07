package com.telemedicine.payment.apidefinitions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.telemedicine.payment.dto.NotificationResponseDto;
import com.telemedicine.payment.dto.PaymentRequest;
import com.telemedicine.payment.entity.Payment;

@Tag(name = "payment",description = "describes payment crud operations on payments")
public interface PaymentApiDefinition {
	
    @Operation(summary = "Adds an payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "paymented added successfully")
    })
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) ;
    
    @Operation(summary = "Get payment by payment Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning the payment  by payment id"),
            @ApiResponse(responseCode = "404",description = "There is no payment with given payment id")
    })
    public ResponseEntity<Payment> getPaymentById(@PathVariable int paymentId);
    
    @Operation(summary = "Get List of payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning all the payments"),
            @ApiResponse(responseCode = "404",description = "No payments")
    })
    public ResponseEntity<List<Payment>> getAllPayments();
    
    @Operation(summary = "Update payment by payment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "updating the payment by payment id"),
            @ApiResponse(responseCode = "404",description = "There is no payment with given payment id")
    })
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentId, @RequestBody Payment payment);
    
    @Operation(summary = "Delete payment by payment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deleting the payment by payment id"),
            @ApiResponse(responseCode = "404",description = "There is no payment with given payment id")
    })
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentId);
    
    @Operation(summary = "Do payment by billing id and account number as request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "updating the payment by succesfull payment"),
            @ApiResponse(responseCode = "404",description = "There is no billing with given billing id")
    })
    public ResponseEntity<NotificationResponseDto> doPayment(@PathVariable int billingId,@RequestBody PaymentRequest paymentRequest);
}
