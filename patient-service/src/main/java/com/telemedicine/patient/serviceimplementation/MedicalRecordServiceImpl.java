package com.telemedicine.patient.serviceimplementation;

import com.telemedicine.patient.dto.MedicalRecordResponse;
import com.telemedicine.patient.entity.PatientEntity;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;
import com.telemedicine.patient.repository.PatientRepository;
import com.telemedicine.patient.service.MedicalRecordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MedicalRecordResponse> getDoctorPrescriptions(String patientId) throws InvalidPatientIdException {
        PatientEntity patientDetails=patientRepository.findByPatientId(patientId).
                orElseThrow(()->new InvalidPatientIdException("invalid patientId"));
        return patientDetails.getMedicalRecordEntity().stream().map(medicalRecordEntity ->
                modelMapper.map(medicalRecordEntity, MedicalRecordResponse.class)).toList();
    }
}
