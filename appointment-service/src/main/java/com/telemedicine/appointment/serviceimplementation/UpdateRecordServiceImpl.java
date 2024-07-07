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
import com.telemedicine.appointment.service.UpdateRecordService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class UpdateRecordServiceImpl implements UpdateRecordService {
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;
    private final PaymentConfiguration paymentConfiguration;
    private final MessageConfiguration messageConfiguration;
    @Override
    @Transactional
    public String update(int appointmentId) throws InvalidAppointmentIdException, InvalidDoctorIdException {
        AppointmentDetailsEntity appointmentDetails = appointmentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(()->new InvalidAppointmentIdException("invalid appointment id: "+appointmentId));
        appointmentDetails.setPaymentStatus(paymentConfiguration.getPaid());
        appointmentDetails.setNotificationStatus(true);
        appointmentRepository.save(appointmentDetails);
        Schedule doctorSchedule = scheduleRepository.findByDoctorId(appointmentDetails.getDoctorId()).orElseThrow(
                ()->new InvalidDoctorIdException("invalid doctor id: "+appointmentDetails.getDoctorId()));
        List<Integer> doctorSlots = doctorSchedule.getSlotsAvailable();
        int slotId = appointmentDetails.getSlotId();
        doctorSlots.removeIf(slotIds -> slotIds == slotId);
        doctorSchedule.setSlotsAvailable(doctorSlots);
        scheduleRepository.save(doctorSchedule);
        slotRepository.deleteById(slotId);
        return messageConfiguration.getSuccess();
    }
}
