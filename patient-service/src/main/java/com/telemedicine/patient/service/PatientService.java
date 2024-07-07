package com.telemedicine.patient.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.telemedicine.patient.dto.Patient;
import com.telemedicine.patient.dto.PatientDto;
import com.telemedicine.patient.dto.UpdateDetailsDto;
import com.telemedicine.patient.entity.PatientEntity;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;

public interface PatientService {
    PatientDto viewProfile(String patientId) throws InvalidPatientIdException;
    PatientDto updateProfile(String patientId, UpdateDetailsDto newDetails) throws InvalidPatientIdException;

}
