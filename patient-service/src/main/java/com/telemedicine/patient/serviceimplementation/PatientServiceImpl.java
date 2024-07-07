package com.telemedicine.patient.serviceimplementation;
import com.telemedicine.patient.dto.AuthRequestDto;
import com.telemedicine.patient.dto.Patient;
import com.telemedicine.patient.dto.PatientDto;
import com.telemedicine.patient.dto.UpdateDetailsDto;
import com.telemedicine.patient.entity.PatientEntity;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;
import com.telemedicine.patient.repository.PatientRepository;
import com.telemedicine.patient.service.PatientService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, AuthRequestDto> requestDtoKafkaTemplate;

    @Override
    public PatientDto viewProfile(String patientId) throws InvalidPatientIdException {
        PatientEntity patient = patientRepository.findByPatientId(patientId).orElseThrow(()->new
                InvalidPatientIdException("patient is not available"));
        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public PatientDto updateProfile(String patientId, UpdateDetailsDto newDetails) throws InvalidPatientIdException {
        PatientEntity oldDetails = patientRepository.findByPatientId(patientId).orElseThrow(()->new
                InvalidPatientIdException("patient is not available"));
        oldDetails.setUserName(newDetails.getUserName());
        oldDetails.setPassword(passwordEncoder.encode(newDetails.getPassword()));
        oldDetails.setEmail(newDetails.getEmail());
        patientRepository.save(oldDetails);
        PatientDto patientResponse = modelMapper.map(oldDetails, PatientDto.class);
        AuthRequestDto authEvent = modelMapper.map(oldDetails,AuthRequestDto.class);
        authEvent.setUserId(oldDetails.getPatientId());
        requestDtoKafkaTemplate.send("patient-topic",authEvent);
        return patientResponse;
    }
}
