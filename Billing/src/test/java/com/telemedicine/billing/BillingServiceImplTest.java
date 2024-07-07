package com.telemedicine.billing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.telemedicine.billing.dto.AppointmentDto;
import com.telemedicine.billing.dto.BillingResponseDto;
import com.telemedicine.billing.entity.Billing;
import com.telemedicine.billing.exception.ResourceNotFoundException;
import com.telemedicine.billing.repo.BillingRepository;
import com.telemedicine.billing.serviceimpl.BillingServiceImpl;

class BillingServiceImplTest {

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BillingServiceImpl billingService;
    

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testGetBillingById() {
        int billingId = 1;
        Billing billing = new Billing();
        billing.setBillNumber(billingId);
        when(billingRepository.findById(billingId)).thenReturn(Optional.of(billing));
        Billing result = billingService.getBillingById(billingId);
        assertNotNull(result);
        assertEquals(billingId, result.getBillNumber());
    }

    @Test
    void testGetAllBillings() {
        List<Billing> billingList = new ArrayList<>();
        billingList.add(new Billing(1,1,"Paid",LocalDate.now(),100));
        billingList.add(new Billing(2,1,"Paid",LocalDate.now(),100));
        when(billingRepository.findAll()).thenReturn(billingList);
        when(modelMapper.map(any(), eq(BillingResponseDto.class))).thenReturn(new BillingResponseDto(1,1,"Pending",100));
        List<BillingResponseDto> result = billingService.getAllBillings();
        assertNotNull(result);
    }

    @Test
    void testGetBillingByAppointmentId() {
        int appointmentId = 1;
        Billing billing = new Billing();
        billing.setAppointmentId(appointmentId);
        when(billingRepository.findByAppointmentId(appointmentId)).thenReturn(Optional.of(billing));
        when(modelMapper.map(any(), eq(BillingResponseDto.class))).thenReturn(new BillingResponseDto(1,1,"Pending",100));
        BillingResponseDto result = billingService.getBillingByAppointmentId(appointmentId);
        assertNotNull(result);
        assertEquals(appointmentId, result.getAppointmentId());
    }

    @Test
    void testGetBillingByIdNotFound() {
        int billingId = 1;
        when(billingRepository.findById(billingId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> billingService.getBillingById(billingId));
    }

    @Test
    void testGetBillingByAppointmentIdNotFound() {
        int appointmentId = 123;
        when(billingRepository.findByAppointmentId(appointmentId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> billingService.getBillingByAppointmentId(appointmentId));
    }

    @Test
    void testConvertBillingToBillingResponse() {
        Billing billing = new Billing();
        when(modelMapper.map(any(), eq(BillingResponseDto.class))).thenReturn(new BillingResponseDto());
        BillingResponseDto result = billingService.convertBillingToBillingResponse(billing);
        assertNotNull(result);
    }
    
    @Test
    void testKafkaListener() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(1);
        appointmentDto.setConsultationFees(50);
        billingService.consume(appointmentDto);
        verify(billingRepository, times(1)).save(any(Billing.class));
    }
}
