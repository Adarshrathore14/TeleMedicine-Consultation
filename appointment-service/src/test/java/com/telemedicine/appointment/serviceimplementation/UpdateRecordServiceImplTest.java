package com.telemedicine.appointment.serviceimplementation;
import com.telemedicine.appointment.configurations.MessageConfiguration;
import com.telemedicine.appointment.configurations.PaymentConfiguration;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import com.telemedicine.appointment.entity.Schedule;
import com.telemedicine.appointment.exceptions.InvalidAppointmentIdException;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.repository.AppointmentRepository;
import com.telemedicine.appointment.repository.ScheduleRepository;
import com.telemedicine.appointment.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UpdateRecordServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private SlotRepository slotRepository;
    @Mock
    private PaymentConfiguration paymentConfiguration;
    @Mock
    private MessageConfiguration messageConfiguration;
    @InjectMocks
    private UpdateRecordServiceImpl updateRecordService;
    private AppointmentDetailsEntity appointmentDetails;
    private Schedule scheduleEntity;
    @BeforeEach
    void setUp() {
        appointmentDetails = new AppointmentDetailsEntity(1,"123",1,1, LocalDate.now(),
                "paid",true,null, LocalDateTime.now());
        List<Integer> doctorSlots = Arrays.asList(1,2);
        scheduleEntity=new Schedule();
        scheduleEntity.setDoctorId(1);
        scheduleEntity.setScheduleDate(LocalDate.now());
        scheduleEntity.setScheduleId(1);
        scheduleEntity.setSlotsAvailable(doctorSlots);
    }

    @Test
    void update() throws InvalidAppointmentIdException, InvalidDoctorIdException {
        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.of(appointmentDetails));
        when(paymentConfiguration.getPaid()).thenReturn("paid");
        when(appointmentRepository.save(appointmentDetails)).thenReturn(appointmentDetails);
        when(scheduleRepository.findByDoctorId(appointmentDetails.getDoctorId())).thenReturn(Optional.of(scheduleEntity));
        List<Integer> originalDoctorSlots = scheduleEntity.getSlotsAvailable();
        List<Integer> updatedDoctorSlots = new ArrayList<>(originalDoctorSlots);
        updatedDoctorSlots.removeIf(slotId -> slotId == 1);
        scheduleEntity.setSlotsAvailable(updatedDoctorSlots);
        when(scheduleRepository.save(scheduleEntity)).thenReturn(scheduleEntity);
        doNothing().when(slotRepository).deleteById(anyInt());
        when(messageConfiguration.getSuccess()).thenReturn("success");
        assertEquals("success",updateRecordService.update(1));
   }
    @Test
    void updateInvalidAppointmentId(){
        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.empty());
        assertThrows(InvalidAppointmentIdException.class,()->updateRecordService.update(1));
    }
    @Test
    void updateInvalidDoctorId(){
        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.of(appointmentDetails));
        when(paymentConfiguration.getPaid()).thenReturn("paid");
        when(appointmentRepository.save(appointmentDetails)).thenReturn(appointmentDetails);
        when(scheduleRepository.findByDoctorId(appointmentDetails.getDoctorId())).thenReturn(Optional.empty());
        assertThrows(InvalidDoctorIdException.class,()->updateRecordService.update(1));
    }
}