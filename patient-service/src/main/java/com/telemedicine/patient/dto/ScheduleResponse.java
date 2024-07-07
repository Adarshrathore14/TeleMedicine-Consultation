package com.telemedicine.patient.dto;
import com.telemedicine.patient.entity.SlotTiming;
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
