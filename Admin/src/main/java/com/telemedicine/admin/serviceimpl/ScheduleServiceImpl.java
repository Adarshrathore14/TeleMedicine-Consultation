package com.telemedicine.admin.serviceimpl;

import com.telemedicine.admin.dto.AppointmentFromAppointment;
import com.telemedicine.admin.dto.DoctorDetailsResponse;
import com.telemedicine.admin.dto.ScheduleResponse;
import com.telemedicine.admin.entity.Schedule;
import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.exception.ResourceNotFoundException;
import com.telemedicine.admin.externalservice.DoctorFeignClients;
import com.telemedicine.admin.repository.ScheduleRepository;
import com.telemedicine.admin.service.ScheduleService;
import com.telemedicine.admin.service.SlotTimingService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private  SlotTimingService slotTimingService;
    
    @Autowired
    private DoctorFeignClients doctorFeignClient;

    @Override
    public List<ScheduleResponse> getAllSchedules() {
    	List<Schedule> schedules=scheduleRepository.findAll();
    	if(schedules.isEmpty())
    	{
    		throw new ResourceNotFoundException("There is No schedule present.The schedule list is empty");
    	}
    	List<ScheduleResponse> scheduleResponseList=new ArrayList<>();
    	for(Schedule schedule:schedules)
    	{
    		scheduleResponseList.add(convertScheduleToScheduleResponse(schedule));
    	}
    	return scheduleResponseList;
    }

    @Override
    public ScheduleResponse getScheduleById(int id) {
        Schedule  schedule=scheduleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("There is no schedule with given schedule id:"+id));
        return convertScheduleToScheduleResponse(schedule);
    }

    @Override
    public ScheduleResponse saveSchedule(Schedule schedule) {
        return convertScheduleToScheduleResponse(scheduleRepository.save(schedule));
    }

    @Override
    public ScheduleResponse updateSchedule(int id, Schedule updatedSchedule) {
        if (scheduleRepository.existsById(id)) {
            updatedSchedule.setScheduleId(id);
            return convertScheduleToScheduleResponse(scheduleRepository.save(updatedSchedule));
        }
        else
        {
        	throw new ResourceNotFoundException("There is No schedule present for given schedule Id:"+id);
        }
    }

    @Override
    public void deleteSchedule(int id) {
        scheduleRepository.deleteById(id);
    }

	@Override
	public List<ScheduleResponse> getScheduleByDate(LocalDate date) {
		List<Schedule> scheduleList=scheduleRepository.findByScheduleDate(date);
		if(scheduleList.isEmpty())
			throw new ResourceNotFoundException("There is no slots available on date="+date);
		List<ScheduleResponse> scheduleResponseList=new ArrayList<>();
    	for(Schedule schedule:scheduleList)
    	{
    		scheduleResponseList.add(convertScheduleToScheduleResponse(schedule));
    	}
		return scheduleResponseList;
	}
	
	public ScheduleResponse convertScheduleToScheduleResponse(Schedule schedule)
	{
		ScheduleResponse scheduleResponse=modelMapper.map(schedule,ScheduleResponse.class);
		DoctorDetailsResponse response=modelMapper.map(doctorFeignClient.getDoctorById(schedule.getDoctorId()).getBody(), DoctorDetailsResponse.class);
		scheduleResponse.setDoctor(response);
		scheduleResponse.getSlotsAvailable().clear();
        for(Integer slotId:schedule.getSlotsAvailable())
        {
        	SlotTiming slottiming=slotTimingService.getSlotTimingById(slotId);
        	scheduleResponse.getSlotsAvailable().add(slottiming);
        }
        return scheduleResponse;
	}

	@Override
	public List<ScheduleResponse> getSchedulesDoctorId(int id) {
		List<Schedule> schedules=scheduleRepository.findByDoctorId(id);
		if(schedules.isEmpty())
			throw new ResourceNotFoundException("There is no schedules available for doctor id="+id);
		List<ScheduleResponse> scheduleResponseList=new ArrayList<>();
    	for(Schedule schedule:schedules)
    	{
    		scheduleResponseList.add(convertScheduleToScheduleResponse(schedule));
    	}
    	return scheduleResponseList;
	}

	@Override
	public void removeBookedSlot(AppointmentFromAppointment appointment) {
		Optional<Schedule> schedule=scheduleRepository.findByDoctorIdAndScheduleDate(appointment.getDoctorId(),appointment.getAppointmentDate());
		System.out.println("came to almost final");
		if(schedule.isEmpty())
			throw new ResourceNotFoundException("there is no schedule avialbale for the given date and appointment id");
		Schedule oldSchedule=scheduleRepository.findById(schedule.get().getScheduleId()).orElseThrow(()->new ResourceNotFoundException("There is no schedule with given schedule id"));
		List<Integer> slots=oldSchedule.getSlotsAvailable();
		slots.remove(Integer.valueOf(appointment.getSlotId()));
		scheduleRepository.save(oldSchedule);
	}
	
}
