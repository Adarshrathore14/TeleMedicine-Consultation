package com.telemedicine.DoctorService.serviceImpl;
import com.telemedicine.DoctorService.Exceptions.ResourceNotFoundException;
import com.telemedicine.DoctorService.dtos.*;
import com.telemedicine.DoctorService.externalService.MedicalRecordFeign;
import com.telemedicine.DoctorService.model.AppointmentDetailsEntity;
import com.telemedicine.DoctorService.model.DoctorEntity;
import com.telemedicine.DoctorService.model.PatientEntity;
import com.telemedicine.DoctorService.repository.DoctorServiceRepository;
import com.telemedicine.DoctorService.repository.PatientRepository;
import com.telemedicine.DoctorService.service.DoctorService_service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class DoctorService_serviceImpl implements DoctorService_service {

	@Autowired
	private DoctorServiceRepository doctorRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private MedicalRecordFeign medicalFeign;

	@Override
	public DoctorResponse addDoctor(DoctorEntity doctor){
		doctorRepository.save(doctor);
		DoctorResponse doctorResponse = modelMapper.map(doctor, DoctorResponse.class);
		return doctorResponse;
	}

	@Override
	public DoctorResponse updateDoctorProfile(int doctorId, DoctorEntity doctor) {
		DoctorEntity doctorEntity = doctorRepository.findByDoctorId(doctorId).orElseThrow(()->new ResourceNotFoundException("invalid doctorId"));
		doctorEntity.setDoctorName(doctor.getDoctorName());
		doctorEntity.setConsultantFee(doctor.getConsultantFee());
		doctorRepository.save(doctorEntity);
		DoctorResponse doctorResponse = modelMapper.map(doctorEntity, DoctorResponse.class);
		return doctorResponse;
	}

	@Override
	public DoctorResponse viewProfile(int doctorId) {
		DoctorEntity doctorEntity = doctorRepository.findByDoctorId(doctorId).orElseThrow(()->new ResourceNotFoundException("invalid doctorId"));
		DoctorResponse doctorResponse = modelMapper.map(doctorEntity, DoctorResponse.class);
		return doctorResponse;
	}
	@Override
	public List<DoctorResponse> viewAllDoctorProfiles(){
		List<DoctorResponse> doctorResponseList = new ArrayList<>();
		for(DoctorEntity doctor : doctorRepository.findAll()){
			doctorResponseList.add(modelMapper.map(doctor,DoctorResponse.class));
		}
		return doctorResponseList;
	}

	@Override
	public void deleteDoctor(int doctorId) {
		DoctorEntity doctor = doctorRepository.findByDoctorId(doctorId).orElseThrow(()->new ResourceNotFoundException("invalid data"));
		doctorRepository.deleteById(doctorId);
	}

	@Override
	public double getConsultationFees(int doctorId) {
		DoctorEntity doctorEntity = doctorRepository.findByDoctorId(doctorId).orElseThrow(()->new ResourceNotFoundException("invalid doctorId"));
		return doctorEntity.getConsultantFee();
	}

	@Override
	public ResponseEntity<MedicalRecordResponse> createMedicalRecord(MedicalRecord medicalRecord) {
		return medicalFeign.createMedicalRecord(medicalRecord);
	}

	@Override
	public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(int medicalId, MedicalDescription medicalDescription) {
		return medicalFeign.updateMedicalDescription(medicalId,medicalDescription);
	}

	@Override
	public ResponseEntity<MedicalRecordResponses> getAllMedicalRecords(int doctorId) {
		return medicalFeign.getAllMedicalRecords(doctorId);
	}

	@Override
	public ResponseEntity<MedicalRecordResponse> getMedicalRecordByMedicalId(int medicalId) {
		return medicalFeign.getMedicalRecordById(medicalId);
	}

	@Override
	public List<AppointmentResponse> viewAppointments(int doctorId) {
		List<AppointmentResponse> appointmentResponses = new ArrayList<>();
		DoctorEntity doctorEntity = doctorRepository.findByDoctorId(doctorId).orElseThrow(()->new ResourceNotFoundException("invalid doctorId"));
		List<AppointmentDetailsEntity> patientsList = doctorEntity.getPatientAppointments().stream().
				filter(appointmentDetailsEntity -> appointmentDetailsEntity.getPaymentStatus().equals("paid")).toList();
		for(AppointmentDetailsEntity appointmentDetails: patientsList){
			AppointmentResponse appointmentResponse = modelMapper.map(appointmentDetails, AppointmentResponse.class);
			appointmentResponse.setPatientDetails(patientRepository.findByPatientId(appointmentDetails.getPatientId()));
			appointmentResponses.add(appointmentResponse);
		}
		return appointmentResponses;
	}
}
