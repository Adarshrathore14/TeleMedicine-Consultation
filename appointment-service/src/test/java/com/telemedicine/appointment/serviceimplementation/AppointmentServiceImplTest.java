package com.telemedicine.appointment.serviceimplementation;

import com.telemedicine.appointment.configurations.PaymentConfiguration;
import com.telemedicine.appointment.dto.AppointmentDto;
import com.telemedicine.appointment.dto.AppointmentResponse;
import com.telemedicine.appointment.dto.BillingResponseDto;
import com.telemedicine.appointment.dto.NotificationResponse;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import com.telemedicine.appointment.exceptions.*;
import com.telemedicine.appointment.feignresponse.BillingFeignService;
import com.telemedicine.appointment.feignresponse.DoctorFeignService;
import com.telemedicine.appointment.repository.AppointmentRepository;
import com.telemedicine.appointment.repository.ScheduleRepository;
import com.telemedicine.appointment.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {
    @Mock
    private PaymentConfiguration paymentConfiguration;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private DoctorFeignService doctorFeignService;
    @Mock
    private BillingFeignService billingFeignService;
    @Mock
    private KafkaTemplate<String, AppointmentDto> kafkaTemplate;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SlotRepository slotRepository;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    private AppointmentDetailsEntity appointmentDetails;

    @BeforeEach
    void setUp() {
        appointmentDetails = new AppointmentDetailsEntity(1,"123",1,1, LocalDate.now(),
                "paid",false,LocalDateTime.now(), null);
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(appointmentDetails.getAppointmentId());
        appointmentDto.setConsultationFees(10.00);
        BillingResponseDto billingResponseDto = new BillingResponseDto();
        billingResponseDto.setAppointmentId(appointmentDetails.getAppointmentId());
        billingResponseDto.setBillNumber(1);
        billingResponseDto.setConsultationFees(10.00);
        BillingResponseDto negativeResponse = new BillingResponseDto();
        negativeResponse.setAppointmentId(0);
        negativeResponse.setBillNumber(-1);
        negativeResponse.setConsultationFees(0.00);
        AppointmentResponse appointmentResponse = new AppointmentResponse();
        appointmentResponse.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointmentResponse.setAppointmentId(appointmentDetails.getAppointmentId());
        appointmentResponse.setBillNumber(1);
        appointmentResponse.setConsultationFees(10.00);
        appointmentResponse.setDoctorId(appointmentDetails.getDoctorId());
        appointmentResponse.setPatientId(appointmentDetails.getPatientId());
    }

//    @Test
//    void createAppointment() throws InvalidDoctorIdException, ServiceUnavailableException {
//        when(scheduleRepository.existsByDoctorId(appointmentDetails.getDoctorId())).thenReturn(true);
//        when(slotRepository.existsBySlotId(appointmentDetails.getSlotId()).thenReturn(true);
//        when(paymentConfiguration.getPending()).thenReturn("pending");
//        when(doctorFeignService.getConsultantFeeByDoctorId(appointmentDetails.getDoctorId())).thenReturn(10.00);
//        when(appointmentRepository.save(appointmentDetails)).thenReturn(appointmentDetails);
//        when(modelMapper.map(appointmentDetails, AppointmentDto.class)).thenReturn(appointmentDto);
//        appointmentDto.setConsultationFees(10.00);
//        kafkaTemplate.send(eq("topic"), any(AppointmentDto.class));
//        when(billingFeignService.getBillingByAppointmentId(appointmentDetails.getAppointmentId())).thenReturn(ResponseEntity.ok(billingResponseDto));
//        when(ResponseEntity.ok(billingResponseDto).getBody()).thenReturn(billingResponseDto);
//        when(modelMapper.map(billingResponseDto,AppointmentResponse.class)).thenReturn(appointmentResponse);
//        assertNotNull(appointmentService.createAppointment(1,"123",appointmentDetails));
//    }
    @Test
    void createAppointmentInvalidDoctorId(){
        when(scheduleRepository.existsByDoctorId(appointmentDetails.getDoctorId())).thenReturn(false);
        assertThrows(InvalidDoctorIdException.class,()->appointmentService.createAppointment(1,"123",appointmentDetails));
    }
    @Test
    void createAppointmentInvalidSlotId(){
        when(scheduleRepository.existsByDoctorId(appointmentDetails.getDoctorId())).thenReturn(true);
        when(slotRepository.existsBySid(appointmentDetails.getSlotId())).thenReturn(false);
        assertThrows(InvalidSlotIdException.class,()->appointmentService.createAppointment(1,"123",appointmentDetails));
    }
    @Test
    void createAppointmentAppointmentAlreadyExists(){
        when(scheduleRepository.existsByDoctorId(appointmentDetails.getDoctorId())).thenReturn(true);
        when(slotRepository.existsBySid(appointmentDetails.getSlotId())).thenReturn(true);
        when(appointmentRepository.existsByPatientIdAndAppointmentDate(appointmentDetails.getPatientId(),appointmentDetails.getAppointmentDate()))
                .thenReturn(true);
        assertThrows(AppointmentAlreadyExistsException.class,()->appointmentService.createAppointment(1,"123",appointmentDetails));

    }
    @Test
    void createAppointmentDoctorServiceUnavailable(){
        when(scheduleRepository.existsByDoctorId(appointmentDetails.getDoctorId())).thenReturn(true);
        when(slotRepository.existsBySid(appointmentDetails.getSlotId())).thenReturn(true);
        when(appointmentRepository.existsByPatientIdAndAppointmentDate(appointmentDetails.getPatientId(),appointmentDetails.getAppointmentDate()))
                .thenReturn(false);
        when(paymentConfiguration.getPending()).thenReturn("pending");
        when(doctorFeignService.getConsultantFeeByDoctorId(appointmentDetails.getDoctorId())).thenReturn(-1.00);
        assertThrows(ServiceUnavailableException.class,()->appointmentService.createAppointment(1,"123",appointmentDetails));
    }
    @Test
    void getAppointmentDetails() throws InvalidAppointmentIdException {
        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.of(appointmentDetails));
        when(modelMapper.map(appointmentDetails, NotificationResponse.class)).thenReturn(new NotificationResponse("123",1));
        assertEquals(new NotificationResponse("123",1),appointmentService.getAppointmentDetails(1));
    }
    @Test
    void getAppointmentDetailsNegativeCase(){
        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.empty());
        assertThrows(InvalidAppointmentIdException.class,()->appointmentService.getAppointmentDetails(1));
    }
//    @Test
//    void createAppointmentBillingServiceDown(){
//        when(scheduleRepository.existsByDoctorId(appointmentDetails.getDoctorId())).thenReturn(true);
//        when(paymentConfiguration.getPending()).thenReturn("pending");
//        when(doctorFeignService.getConsultantFeeByDoctorId(appointmentDetails.getDoctorId())).thenReturn(10.00);
//        when(appointmentRepository.save(appointmentDetails)).thenReturn(appointmentDetails);
//        when(modelMapper.map(appointmentDetails, AppointmentDto.class)).thenReturn(appointmentDto);
//        kafkaTemplate.send("topic",appointmentDto);
//        when(billingFeignService.getBillingByAppointmentId(appointmentDetails.getAppointmentId())).thenReturn(ResponseEntity.ok(negativeResponse));
//        assertThrows(ServiceUnavailableException.class,()->appointmentService.createAppointment(1,"123",appointmentDetails));
//    }
}