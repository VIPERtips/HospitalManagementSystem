package com.example.HospitalManagementSystem.service;

import com.example.HospitalManagementSystem.model.PatientVitals;
import com.example.HospitalManagementSystem.repository.PatientVitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientVitalsService {
	@Autowired
	private PatientVitalsRepository patientVitalsRepository;

	public PatientVitals save(PatientVitals vitals) {
		return patientVitalsRepository.save(vitals);
	}

	public List<PatientVitals> findByPatientId(int patientId) {
		return patientVitalsRepository.findByPatientId(patientId);
	}

	// Added method to find vital signs by nurse ID**
	public List<PatientVitals> findByNurseId(int nurseId) {
		return patientVitalsRepository.findByNurseId(nurseId); // **Utilizes the repository method**
	}

	// **Added method to find vital signs by doctor ID**
	public List<PatientVitals> findByDoctorId(int doctorId) {
		return patientVitalsRepository.findByDoctorId(doctorId);
	}
}
