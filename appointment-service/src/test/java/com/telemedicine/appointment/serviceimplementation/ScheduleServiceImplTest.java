package com.telemedicine.appointment.serviceimplementation;
import com.telemedicine.appointment.dto.ScheduleResponse;
import com.telemedicine.appointment.entity.Schedule;
import com.telemedicine.appointment.entity.SlotTiming;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.exceptions.NoSchedulesAvailableException;
import com.telemedicine.appointment.repository.ScheduleRepository;
import com.telemedicine.appointment.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private SlotRepository slotRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ScheduleServiceImpl scheduleService;
    private List<Schedule> scheduleEntityList;
    private Schedule scheduleEntity;
    private ScheduleResponse scheduleResponse;
    private SlotTiming slotTimingEntity;
    @BeforeEach
    void setUp() {
        scheduleEntity=new Schedule(1,1,Arrays.asList(1,2), LocalDate.now());
        slotTimingEntity = new SlotTiming(1, LocalTime.now(),LocalTime.now());
        scheduleResponse = new ScheduleResponse();
        scheduleResponse.setDoctorId(2);
        scheduleResponse.setScheduleDate(LocalDate.now());
        scheduleResponse.setScheduleId(2);
        scheduleResponse.setSlotsAvailable(null);
        scheduleEntityList = Arrays.asList(new Schedule(1,1,Arrays.asList(1,2), LocalDate.now()),
                new Schedule(2,2,Arrays.asList(1,2), LocalDate.now()));
    }

    @Test
    void getAllSchedulesByDate() throws NoSchedulesAvailableException {
        when(scheduleRepository.findAllByScheduleDate(LocalDate.now())).thenReturn(Optional.of(scheduleEntityList));
        when(slotRepository.findBySid(1)).thenReturn(slotTimingEntity);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleResponse.class))).thenReturn(new ScheduleResponse());
        assertEquals(scheduleEntityList.size(),scheduleService.getAllSchedulesByDate(LocalDate.now()).size());
    }
    @Test
    void getAllSchedulesByDateNegativeCase(){
        when(scheduleRepository.findAllByScheduleDate(LocalDate.now())).thenReturn(Optional.empty());
        assertThrows(NoSchedulesAvailableException.class,()->scheduleService.getAllSchedulesByDate(LocalDate.now()));
    }

    @Test
    void getSchedulesByDoctorId() throws InvalidDoctorIdException, NoSchedulesAvailableException {
        when(scheduleRepository.existsByDoctorId(1)).thenReturn(true);
        when(scheduleRepository.findByDoctorId(1)).thenReturn(Optional.of(scheduleEntity));
        when(slotRepository.findBySid(1)).thenReturn(slotTimingEntity);
        when(modelMapper.map(any(),eq(ScheduleResponse.class))).thenReturn(scheduleResponse);
        assertNotNull(scheduleService.getSchedulesByDoctorId(1));
    }
    @Test
    void getSchedulesByDoctorId_InvalidDoctorId(){
        when(scheduleRepository.existsByDoctorId(2)).thenReturn(false);
        assertThrows(InvalidDoctorIdException.class,()->scheduleService.getSchedulesByDoctorId(2));
    }
    @Test
    void getSchedulesByDoctorId_NoSchedulesAvailable(){
        when(scheduleRepository.existsByDoctorId(1)).thenReturn(true);
        when(scheduleRepository.findByDoctorId(1)).thenReturn(Optional.empty());
        assertThrows(NoSchedulesAvailableException.class,()->scheduleService.getSchedulesByDoctorId(1));
    }

    @Test
    void getSchedulesByDoctorIdAndDate() throws InvalidDoctorIdException, NoSchedulesAvailableException {
        when(scheduleRepository.existsByDoctorId(1)).thenReturn(true);
        when(scheduleRepository.findByDoctorIdAndScheduleDate(1,LocalDate.now())).thenReturn(Optional.of(scheduleEntity));
        when(slotRepository.findBySid(1)).thenReturn(slotTimingEntity);
        when(modelMapper.map(any(),eq(ScheduleResponse.class))).thenReturn(scheduleResponse);
        assertNotNull(scheduleService.getSchedulesByDoctorIdAndDate(1,LocalDate.now()));
    }
    @Test
    void getSchedulesByDoctorIdAndScheduleDate_InvalidDoctorId(){
        when(scheduleRepository.existsByDoctorId(2)).thenReturn(false);
        assertThrows(InvalidDoctorIdException.class,()->scheduleService.getSchedulesByDoctorIdAndDate(2,LocalDate.now()));
    }
    @Test
    void getSchedulesByDoctorIdAndScheduleDate_NoSchedulesAvailable(){
        when(scheduleRepository.existsByDoctorId(1)).thenReturn(true);
        when(scheduleRepository.findByDoctorIdAndScheduleDate(1,LocalDate.now())).thenReturn(Optional.empty());
        assertThrows(NoSchedulesAvailableException.class,()->scheduleService.getSchedulesByDoctorIdAndDate(1,LocalDate.now()));
    }
}