package com.telemedicine.appointment.service;
import com.telemedicine.appointment.exceptions.InvalidAppointmentIdException;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
public interface UpdateRecordService {
    String update(int appointmentId) throws InvalidAppointmentIdException, InvalidDoctorIdException;
    /* updates
     * changing the status of payment and notification
     * deleting the slot-value from the list and from the slotTable
     */
}
