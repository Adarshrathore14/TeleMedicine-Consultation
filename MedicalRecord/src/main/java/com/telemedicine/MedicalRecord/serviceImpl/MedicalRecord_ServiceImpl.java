package com.telemedicine.MedicalRecord.serviceImpl;
import com.telemedicine.MedicalRecord.Exceptions.ResourceNotFoundException;
import com.telemedicine.MedicalRecord.dtos.MedicalDescription;
import com.telemedicine.MedicalRecord.dtos.MedicalRecordResponse;
import com.telemedicine.MedicalRecord.dtos.MedicalRecordResponses;
import com.telemedicine.MedicalRecord.model.MedicalRecordEntity;
import com.telemedicine.MedicalRecord.service.MedicalRecord_Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.telemedicine.MedicalRecord.repository.MedicalRecord_Repository;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecord_ServiceImpl implements MedicalRecord_Service {

	@Autowired
	private MedicalRecord_Repository recordRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MedicalRecordResponse createMedicalRecord(MedicalRecordEntity medicalRecordEntity) {
		MedicalRecordEntity medicalRecord = recordRepository.save(medicalRecordEntity);
		return modelMapper.map(medicalRecord, MedicalRecordResponse.class);
	}

	@Override
	public MedicalRecordResponse updateMedicalRecord(int medicalId, MedicalDescription medicalDescription) {
		MedicalRecordEntity medicalRecord = recordRepository.findByMedicalId(medicalId).orElseThrow(()->new ResourceNotFoundException("invalid medical Id"));
		medicalRecord.setMedicalDescription(medicalDescription.getMedicalDescription());
		MedicalRecordEntity medicalRecordEntity = recordRepository.save(medicalRecord);
		return modelMapper.map(medicalRecordEntity, MedicalRecordResponse.class);
	}

	@Override
	public MedicalRecordResponses getAllMedicalRecords(int doctorId) {
		List<MedicalRecordEntity> medicalRecordEntityList = recordRepository.findAllByDoctorId(doctorId).orElseThrow(()->new ResourceNotFoundException("invalid doctorId"));
		return convertToMedicalResponses(doctorId,medicalRecordEntityList);

	}
	@Override
	public  MedicalRecordResponse getMedicalRecordByMedicalId(int medicalId){
		MedicalRecordEntity medicalRecord = recordRepository.findByMedicalId(medicalId).orElseThrow(()->new ResourceNotFoundException("invalid medical Id"));
		return modelMapper.map(medicalRecord, MedicalRecordResponse.class);
	}

	public MedicalRecordResponses convertToMedicalResponses(int doctorId,List<MedicalRecordEntity> medicalRecordEntityList) {
		MedicalRecordResponses medicalRecordResponses = new MedicalRecordResponses();
		List<MedicalRecordResponse> medicalRecordResponseList =new ArrayList<>();
		medicalRecordResponses.setDoctorId(doctorId);
		for(MedicalRecordEntity medicalRecord : medicalRecordEntityList){
			MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
			medicalRecordResponse.setAppointmentId(medicalRecord.getAppointmentId());
			medicalRecordResponse.setMedicalDescription(medicalRecord.getMedicalDescription());
			medicalRecordResponse.setPatientId(medicalRecord.getPatientId());
			medicalRecordResponse.setMedicalId(medicalRecord.getMedicalId());
			medicalRecordResponseList.add(medicalRecordResponse);
		}
		medicalRecordResponses.setMedicalRecordResponseList(medicalRecordResponseList);
		return medicalRecordResponses;
	}
}
