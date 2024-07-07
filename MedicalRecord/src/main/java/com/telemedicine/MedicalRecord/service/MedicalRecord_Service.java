package com.telemedicine.MedicalRecord.service;

import com.telemedicine.MedicalRecord.dtos.MedicalDescription;
import com.telemedicine.MedicalRecord.dtos.MedicalRecordResponse;
import com.telemedicine.MedicalRecord.dtos.MedicalRecordResponses;
import com.telemedicine.MedicalRecord.model.MedicalRecordEntity;

public interface MedicalRecord_Service {
	MedicalRecordResponse createMedicalRecord(MedicalRecordEntity medicalRecordEntity);
	MedicalRecordResponse updateMedicalRecord(int medicalId,MedicalDescription medicalDescription);
	MedicalRecordResponses getAllMedicalRecords(int doctorId);
	MedicalRecordResponse getMedicalRecordByMedicalId(int medicalId);

}
