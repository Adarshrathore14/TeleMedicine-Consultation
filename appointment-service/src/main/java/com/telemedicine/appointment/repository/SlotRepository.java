package com.telemedicine.appointment.repository;
import com.telemedicine.appointment.entity.SlotTiming;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SlotRepository extends JpaRepository<SlotTiming,Integer> {
    //get by slotId (to display the details)
    SlotTiming findBySid(int slotId);
    //exists by slotId (check whether user enters a correct slot id)
    Boolean existsBySid(int slotId);
}
