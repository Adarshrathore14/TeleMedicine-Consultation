package com.telemedicine.patient.serviceimplementation;
import com.telemedicine.patient.dto.AppointmentDetails;
import com.telemedicine.patient.dto.AppointmentResponse;
import com.telemedicine.patient.entity.AppointmentDetailsEntity;
import com.telemedicine.patient.exceptions.*;
import com.telemedicine.patient.feignresponse.AppointmentFeignResponse;
import com.telemedicine.patient.repository.AppointmentRepository;
import com.telemedicine.patient.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentFeignResponse appointmentFeignResponse;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseEntity<AppointmentResponse> createAppointment(int doctorId, AppointmentDetailsEntity appointmentDetails)
            throws InvalidDoctorIdException, AppointmentAlreadyExistsException, ServiceUnavailableException, InvalidSlotIdException {
        return appointmentFeignResponse.addAppointment(doctorId,appointmentDetails);
    }
    @Override
    public List<AppointmentDetails> myAppointments(String patientId) throws NoAppointmentsAvailableException {
        List<AppointmentDetailsEntity> appointmentDetails = appointmentRepository.findAllByPatientId(patientId).
                orElseThrow(()->new NoAppointmentsAvailableException("no appointment available"));
        return appointmentDetails.stream().map(appointment ->
                modelMapper.map(appointment, AppointmentDetails.class)).toList();
    }
}
