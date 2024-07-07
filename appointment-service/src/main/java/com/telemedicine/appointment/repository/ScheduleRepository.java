package com.telemedicine.appointment.repository;
import com.telemedicine.appointment.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    //get all availability by Date;
    Optional<List<Schedule>> findAllByScheduleDate(LocalDate scheduleDate);
    // get all availability of the doctor
    Optional<Schedule> findByDoctorId(int doctorId);
    // exists by doctorId (when the patient enters a wrong doctor Id)
    Boolean existsByDoctorId(int doctorId);
    //get all availability by Date and Doctor
    Optional<Schedule> findByDoctorIdAndScheduleDate(int doctorId,LocalDate scheduleDate);
}
