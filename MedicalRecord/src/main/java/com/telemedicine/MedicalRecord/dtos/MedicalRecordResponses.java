package com.telemedicine.MedicalRecord.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponses {
	private int doctorId;
	private List<MedicalRecordResponse> medicalRecordResponseList;
}
