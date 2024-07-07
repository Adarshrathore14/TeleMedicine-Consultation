package com.telemedicine.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.telemedicine.admin.dto.DoctorDetailsResponse;
import com.telemedicine.admin.dto.DoctorResponse;
import com.telemedicine.admin.dto.ScheduleResponse;
import com.telemedicine.admin.entity.Schedule;
import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.exception.ResourceNotFoundException;
import com.telemedicine.admin.externalservice.DoctorFeignClients;
import com.telemedicine.admin.repository.ScheduleRepository;
import com.telemedicine.admin.service.SlotTimingService;
import com.telemedicine.admin.serviceimpl.ScheduleServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScheduleServiceImplTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SlotTimingService slotTimingService;

    @Mock
    private DoctorFeignClients doctorFeignClient;

    @BeforeEach
   	public void setup() {
   		MockitoAnnotations.openMocks(this);
   	}

    @Test
    public void testGetAllSchedules() {
    	Schedule schedule=new Schedule(1,1,new ArrayList<Integer>(),null);
    	ScheduleResponse scheduleResponse=new ScheduleResponse(1,null,new ArrayList<SlotTiming>(),LocalDate.now());
        List<Schedule> mockSchedules = Arrays.asList(schedule, schedule);
        when(scheduleRepository.findAll()).thenReturn(mockSchedules);
        when(doctorFeignClient.getDoctorById(1)).thenReturn(new ResponseEntity<DoctorResponse>(new DoctorResponse(),HttpStatus.OK));
        when(modelMapper.map(schedule,ScheduleResponse.class)).thenReturn(scheduleResponse);
        when(modelMapper.map(doctorFeignClient.getDoctorById(1).getBody(), DoctorDetailsResponse.class)).thenReturn(new DoctorDetailsResponse());
        

        
        List<ScheduleResponse> result = scheduleService.getAllSchedules();

        
        assertEquals(mockSchedules.size(), result.size());
    }

    @Test
    public void testGetScheduleById() {
        
        int scheduleId = 1;
    	Schedule schedule=new Schedule(1,1,new ArrayList<Integer>(),null);
    	DoctorResponse doctorResponse=new DoctorResponse(1,"Naveen",250);
        ScheduleResponse scheduleResponse=new ScheduleResponse(1,null,new ArrayList<SlotTiming>(),LocalDate.now());
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
       
        when(doctorFeignClient.getDoctorById(1)).thenReturn(new ResponseEntity<DoctorResponse>(doctorResponse,HttpStatus.OK));
        when(modelMapper.map(schedule,ScheduleResponse.class)).thenReturn(scheduleResponse);
        when(modelMapper.map(doctorFeignClient.getDoctorById(1).getBody(), DoctorDetailsResponse.class)).thenReturn(new DoctorDetailsResponse());

        
        ScheduleResponse result = scheduleService.getScheduleById(scheduleId);

        
        assertNotNull(result);
    }

    @Test
    public void testSaveSchedule() {
        
    	Schedule schedule=new Schedule(1,1,new ArrayList<Integer>(),null);
        when(scheduleRepository.save(any())).thenReturn(schedule);
        ScheduleResponse scheduleResponse=new ScheduleResponse(1,null,new ArrayList<SlotTiming>(),LocalDate.now());
        when(doctorFeignClient.getDoctorById(1)).thenReturn(new ResponseEntity<DoctorResponse>(new DoctorResponse(),HttpStatus.OK));
        when(modelMapper.map(schedule,ScheduleResponse.class)).thenReturn(scheduleResponse);
        when(modelMapper.map(doctorFeignClient.getDoctorById(1).getBody(), DoctorDetailsResponse.class)).thenReturn(new DoctorDetailsResponse());

        
        ScheduleResponse result = scheduleService.saveSchedule(schedule);

        
        assertNotNull(result);
    }

    @Test
    public void testUpdateSchedule() {
        
        int scheduleId = 1;
        Schedule schedule=new Schedule(1,1,new ArrayList<Integer>(),null);
        DoctorResponse doctorResponse=new DoctorResponse(1,"Naveen",250);
        ScheduleResponse scheduleResponse=new ScheduleResponse(1,null,new ArrayList<SlotTiming>(),LocalDate.now());
        when(scheduleRepository.existsById(scheduleId)).thenReturn(true);
        when(scheduleRepository.save(any())).thenReturn(schedule);
        when(doctorFeignClient.getDoctorById(1)).thenReturn(new ResponseEntity<DoctorResponse>(doctorResponse,HttpStatus.OK));
        when(modelMapper.map(schedule,ScheduleResponse.class)).thenReturn(scheduleResponse);
        when(modelMapper.map(doctorFeignClient.getDoctorById(1).getBody(), DoctorDetailsResponse.class)).thenReturn(new DoctorDetailsResponse());
       

        
        ScheduleResponse result = scheduleService.updateSchedule(scheduleId, schedule);

        
        assertNotNull(result);
    }

    @Test
    public void testDeleteSchedule() {
        
        int scheduleId = 1;

        
        scheduleService.deleteSchedule(scheduleId);

        
        verify(scheduleRepository).deleteById(scheduleId);
    }

    @Test
    public void testGetScheduleByDate() {
        
        LocalDate date = LocalDate.now();
        Schedule schedule=new Schedule(1,1,new ArrayList<Integer>(),null);
        List<Schedule> mockSchedules = Arrays.asList(schedule,schedule);
        when(scheduleRepository.findByScheduleDate(date)).thenReturn(mockSchedules);
        DoctorResponse doctorResponse=new DoctorResponse(1,"Naveen",250);
        ScheduleResponse scheduleResponse=new ScheduleResponse(1,null,new ArrayList<SlotTiming>(),LocalDate.now());
        when(doctorFeignClient.getDoctorById(1)).thenReturn(new ResponseEntity<DoctorResponse>(doctorResponse,HttpStatus.OK));
        when(modelMapper.map(schedule,ScheduleResponse.class)).thenReturn(scheduleResponse);
        when(modelMapper.map(doctorFeignClient.getDoctorById(1).getBody(), DoctorDetailsResponse.class)).thenReturn(new DoctorDetailsResponse());

        
        List<ScheduleResponse> result = scheduleService.getScheduleByDate(date);

        
        assertEquals(mockSchedules.size(), result.size());
    }

    @Test
    public void testGetSchedulesDoctorId() {
        
        int doctorId = 1;
        Schedule schedule=new Schedule(1,1,new ArrayList<Integer>(),null);
        List<Schedule> mockSchedules = Arrays.asList(schedule,schedule);
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(mockSchedules);
        
        ScheduleResponse scheduleResponse=new ScheduleResponse(1,null,new ArrayList<SlotTiming>(),LocalDate.now());
        when(doctorFeignClient.getDoctorById(1)).thenReturn(new ResponseEntity<DoctorResponse>(new DoctorResponse(),HttpStatus.OK));
        when(modelMapper.map(schedule,ScheduleResponse.class)).thenReturn(scheduleResponse);
        when(modelMapper.map(doctorFeignClient.getDoctorById(1).getBody(), DoctorDetailsResponse.class)).thenReturn(new DoctorDetailsResponse());

        
        List<ScheduleResponse> result = scheduleService.getSchedulesDoctorId(doctorId);

        
        assertEquals(mockSchedules.size(), result.size());
    }

    

    @Test
    public void testGetScheduleByIdNotFound() {
        
        int scheduleId = 1;
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getScheduleById(scheduleId));
    }

    @Test
    public void testUpdateScheduleNotFound() {
        
        int scheduleId = 1;
        when(scheduleRepository.existsById(scheduleId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.updateSchedule(scheduleId, new Schedule()));
    }

    @Test
    public void testGetScheduleByDateNoSlotsAvailable() {
        
        LocalDate date = LocalDate.now();
        when(scheduleRepository.findByScheduleDate(date)).thenReturn(Collections.emptyList());

        
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getScheduleByDate(date));
    }

    @Test
    public void testGetSchedulesDoctorIdNotFound() {
        
        int doctorId = 1;
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Collections.emptyList());

        
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getSchedulesDoctorId(doctorId));
    }
}

