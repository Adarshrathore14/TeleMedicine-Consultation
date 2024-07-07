package com.telemedicine.appointment.dto;
import com.telemedicine.appointment.entity.SlotTiming;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleResponse {
    private int scheduleId;
    private int doctorId;
    private List<SlotTiming> slotsAvailable;
    private LocalDate scheduleDate;
}
