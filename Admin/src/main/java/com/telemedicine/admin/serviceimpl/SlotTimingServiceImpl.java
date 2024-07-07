package com.telemedicine.admin.serviceimpl;

import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.exception.ResourceNotFoundException;
import com.telemedicine.admin.repository.SlotTimingRepository;
import com.telemedicine.admin.service.SlotTimingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlotTimingServiceImpl implements SlotTimingService
{

    @Autowired
    private SlotTimingRepository slotTimingRepository;

    @Override
    public List<SlotTiming> getAllSlotTimings() {
    	List<SlotTiming> slotTimings=slotTimingRepository.findAll();
    	if(slotTimings.isEmpty())
    		throw new ResourceNotFoundException("There is No slots present");
        return slotTimings;
    }

    @Override
    public SlotTiming getSlotTimingById(int id) {
        Optional<SlotTiming> optionalSlotTiming = slotTimingRepository.findById(id);
        return optionalSlotTiming.orElseThrow(()->new ResourceNotFoundException("There is no slot with given slot id:"+id));
    }

    @Override
    public SlotTiming saveSlotTiming(SlotTiming slotTiming) {
    	if(!isSlotOverlapping(slotTiming))
    		return slotTimingRepository.save(slotTiming);
    	else
    		throw new ResourceNotFoundException("Over lapping in slot timings please check");
    }
    private boolean isSlotOverlapping(SlotTiming newSlot) {
        List<SlotTiming> existingSlots = slotTimingRepository.findAll();

        for (SlotTiming existingSlot : existingSlots) {
            if (isTimeOverlap(existingSlot, newSlot)) {
                return true; // Overlapping slot found
            }
        }

        return false; // No overlapping slots
    }

    private boolean isTimeOverlap(SlotTiming existingSlot, SlotTiming newSlot) {
        // Check if the start time of the new slot is between the start and end time of an existing slot
        boolean startTimeOverlap = !newSlot.getStartTime().isBefore(existingSlot.getStartTime())
                && newSlot.getStartTime().isBefore(existingSlot.getEndTime());

        // Check if the end time of the new slot is between the start and end time of an existing slot
        boolean endTimeOverlap = !newSlot.getEndTime().isBefore(existingSlot.getStartTime())
                && newSlot.getEndTime().isBefore(existingSlot.getEndTime());

        // Check if the new slot completely contains an existing slot
        boolean containsExistingSlot = newSlot.getStartTime().isBefore(existingSlot.getStartTime())
                && newSlot.getEndTime().isAfter(existingSlot.getEndTime());

        // Return true if any overlap condition is met
        return startTimeOverlap || endTimeOverlap || containsExistingSlot;
    }

    @Override
    public SlotTiming updateSlotTiming(int id, SlotTiming updatedSlotTiming) {
        if (slotTimingRepository.existsById(id)) {
            updatedSlotTiming.setSid(id);
            return slotTimingRepository.save(updatedSlotTiming);
        }
        else
        {
        	throw new ResourceNotFoundException("There is No slots present for given slot Id"+id);
        }
    }

    @Override
    public void deleteSlotTiming(int id) {
    	getSlotTimingById(id);
        slotTimingRepository.deleteById(id);
    }
}
