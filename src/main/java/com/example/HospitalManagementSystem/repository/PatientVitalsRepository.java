package com.example.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.PatientVitals;

public interface PatientVitalsRepository extends JpaRepository<PatientVitals, Integer> {

	List<PatientVitals> findByPatientId(int patientId);
	// Added method to find vital signs by nurse ID**
    List<PatientVitals> findByNurseId(int nurseId); // **New method for nurse ID**
    //Added method to find vital signs by doctor ID**
    List<PatientVitals> findByDoctorId(int doctorId); // **New method for doctor ID**

}
