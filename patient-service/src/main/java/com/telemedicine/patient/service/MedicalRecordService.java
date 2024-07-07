package com.telemedicine.patient.service;
import com.telemedicine.patient.dto.MedicalRecordResponse;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;

import java.util.List;
public interface MedicalRecordService {
    List<MedicalRecordResponse> getDoctorPrescriptions(String patientId) throws InvalidPatientIdException;
}
