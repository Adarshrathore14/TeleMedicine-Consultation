package com.telemedicine.appointment.serviceimplementation;
import com.telemedicine.appointment.dto.ScheduleResponse;
import com.telemedicine.appointment.entity.Schedule;
import com.telemedicine.appointment.entity.SlotTiming;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.exceptions.NoSchedulesAvailableException;
import com.telemedicine.appointment.repository.ScheduleRepository;
import com.telemedicine.appointment.repository.SlotRepository;
import com.telemedicine.appointment.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ScheduleResponse> getAllSchedulesByDate(LocalDate date) throws NoSchedulesAvailableException {
        List<Schedule> scheduleEntities = scheduleRepository.findAllByScheduleDate(date).orElseThrow(
                ()->new NoSchedulesAvailableException("no doctor available on this date: "+date));
        return scheduleEntities.stream().map(scheduleEntity -> {
            List<SlotTiming> slotDetailsList = scheduleEntity.getSlotsAvailable().stream().map(slotRepository::findBySid)
                    .filter(slotTiming -> slotTiming!=null).toList();
            ScheduleResponse scheduleResponse = modelMapper.map(scheduleEntity, ScheduleResponse.class);
            scheduleResponse.setSlotsAvailable(slotDetailsList);
            return scheduleResponse;
        }).toList();
    }

    @Override
    public ScheduleResponse getSchedulesByDoctorId(int doctorId) throws NoSchedulesAvailableException,
            InvalidDoctorIdException {
        if(Boolean.FALSE.equals(scheduleRepository.existsByDoctorId(doctorId))){
            throw new InvalidDoctorIdException("invalid doctor id: "+doctorId);
        }
        Schedule scheduleEntity = scheduleRepository.findByDoctorId(doctorId).orElseThrow(
                ()->new NoSchedulesAvailableException("no schedules available for this doctor:  "+doctorId));
        List<SlotTiming> slotTimingDetails = scheduleEntity.getSlotsAvailable().stream().map(slotRepository::findBySid)
                .toList();
        ScheduleResponse scheduleResponse = modelMapper.map(scheduleEntity, ScheduleResponse.class);
        scheduleResponse.setSlotsAvailable(slotTimingDetails);
        return scheduleResponse;
    }

    @Override
    public ScheduleResponse getSchedulesByDoctorIdAndDate(int doctorId, LocalDate availableDate) throws
            NoSchedulesAvailableException, InvalidDoctorIdException {
        if(Boolean.FALSE.equals(scheduleRepository.existsByDoctorId(doctorId))){
            throw new InvalidDoctorIdException("invalid doctor id: "+doctorId);
        }
        Schedule scheduleEntity = scheduleRepository.findByDoctorIdAndScheduleDate(doctorId,availableDate)
                .orElseThrow(()->new NoSchedulesAvailableException("no schedules available for this doctor: "+doctorId +
                        "on this date: "+availableDate));
        List<SlotTiming> slotTimingDetails = scheduleEntity.getSlotsAvailable().stream().map(slotRepository::findBySid)
                .toList();
        ScheduleResponse scheduleResponse = modelMapper.map(scheduleEntity, ScheduleResponse.class);
        scheduleResponse.setSlotsAvailable(slotTimingDetails);
        return scheduleResponse;
    }
}
