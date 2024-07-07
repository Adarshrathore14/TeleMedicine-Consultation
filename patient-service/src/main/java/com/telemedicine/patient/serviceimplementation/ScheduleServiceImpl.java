package com.telemedicine.patient.serviceimplementation;
import com.telemedicine.patient.dto.ScheduleResponse;
import com.telemedicine.patient.exceptions.InvalidDoctorIdException;
import com.telemedicine.patient.exceptions.NoSchedulesAvailableException;
import com.telemedicine.patient.feignresponse.AppointmentFeignResponse;
import com.telemedicine.patient.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final AppointmentFeignResponse appointmentFeignResponse;

    @Override
    public ResponseEntity<List<ScheduleResponse>> getByDate(LocalDate date) throws NoSchedulesAvailableException{
        return appointmentFeignResponse.getByDate(date);
    }

    @Override
    public ResponseEntity<ScheduleResponse> getByDoctorId(int doctorId) throws InvalidDoctorIdException,
            NoSchedulesAvailableException{
        return appointmentFeignResponse.getSchedulesByDoctorId(doctorId,null);
    }
    @Override
    public ResponseEntity<ScheduleResponse> getByDoctorIdAndDate(int doctorId, LocalDate date) throws InvalidDoctorIdException, NoSchedulesAvailableException {
        return appointmentFeignResponse.getSchedulesByDoctorId(doctorId,date);
    }
}
