package com.telemedicine.admin.service;

import java.util.List;

import com.telemedicine.admin.entity.SlotTiming;

public interface SlotTimingService {
    List<SlotTiming> getAllSlotTimings();

    SlotTiming getSlotTimingById(int id);

    SlotTiming saveSlotTiming(SlotTiming slotTiming);

    SlotTiming updateSlotTiming(int id, SlotTiming slotTiming);

    void deleteSlotTiming(int id);
}
