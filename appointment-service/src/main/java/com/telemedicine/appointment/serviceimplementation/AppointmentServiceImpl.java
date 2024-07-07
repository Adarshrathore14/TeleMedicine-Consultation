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
import com.telemedicine.appointment.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final PaymentConfiguration paymentConfiguration;
    private final BillingFeignService billingFeignService;
    private final DoctorFeignService doctorFeignService;
    private final SlotRepository slotRepository;
    private final KafkaTemplate<String, AppointmentDto> streamData;

    @Override
    @Transactional
    public AppointmentResponse createAppointment(int doctorId, String patientId, AppointmentDetailsEntity appointmentDetails)
            throws InvalidDoctorIdException, ServiceUnavailableException, AppointmentAlreadyExistsException, InvalidSlotIdException {
        if(Boolean.FALSE.equals(scheduleRepository.existsByDoctorId(doctorId))){
            throw new InvalidDoctorIdException("invalid doctor id: "+doctorId);
        }
        if(Boolean.FALSE.equals(slotRepository.existsBySid(appointmentDetails.getSlotId()))){
            throw new InvalidSlotIdException("enter a valid slot Id: "+appointmentDetails.getSlotId());
        }
        if(appointmentRepository.existsByPatientIdAndAppointmentDate(patientId,appointmentDetails.getAppointmentDate())){
            throw new AppointmentAlreadyExistsException("appointment already booked for you on this date"+appointmentDetails.getAppointmentDate());
        }
        appointmentDetails.setPatientId(patientId);
        appointmentDetails.setDoctorId(doctorId);
        appointmentDetails.setCreatedDate(LocalDateTime.now());
        appointmentDetails.setNotificationStatus(false);
        appointmentDetails.setPaymentStatus(paymentConfiguration.getPending());
        double consultationFees =doctorFeignService.getConsultantFeeByDoctorId(doctorId);
        if(consultationFees==-1.00){
            throw new ServiceUnavailableException("try again after some Time");
        }
        //getting the consultation fees and checking whether the doctor service throws exception or serviceUnavailable
        AppointmentDto response = modelMapper.map(appointmentRepository.save(appointmentDetails), AppointmentDto.class);
        response.setConsultationFees(consultationFees);
        streamData.send("appointment",response);
        BillingResponseDto billingResponse = billingFeignService.getBillingByAppointmentId(response.getAppointmentId()).getBody();
        if(Objects.requireNonNull(billingResponse).getAppointmentId()==0){
            appointmentRepository.deleteAllByPatientIdAndCreatedDate(appointmentDetails.getPatientId(),
                    appointmentDetails.getCreatedDate());
            throw new ServiceUnavailableException("Try again after some Time");
        }
        AppointmentResponse appointmentResponse = modelMapper.map(billingResponse, AppointmentResponse.class);
        appointmentResponse.setDoctorId(doctorId);
        appointmentResponse.setPatientId(appointmentDetails.getPatientId());
        appointmentResponse.setAppointmentDate(appointmentDetails.getAppointmentDate());
        /*sending the values into the topic (AppointmentResponse( use modelMapper to convert and then Send))
        return the response sent by the billing-service by passing the appointmentId*/
        return appointmentResponse;
    }

    @Override
    public NotificationResponse getAppointmentDetails(int appointmentId) throws InvalidAppointmentIdException {
        AppointmentDetailsEntity appointmentDetails = appointmentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(()->new InvalidAppointmentIdException("invalid appointment Id"));
        return modelMapper.map(appointmentDetails, NotificationResponse.class);
    }

//	@Override
//	public List<AppointmentDetailsEntity> getAllAppointments() {
//		return appointmentRepository.findAll();
//	}
}