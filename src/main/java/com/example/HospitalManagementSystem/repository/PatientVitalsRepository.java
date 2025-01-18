package com.example.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.PatientVitals;

public interface PatientVitalsRepository extends JpaRepository<PatientVitals, Integer> {

	List<PatientVitals> findByPatientId(int patientId);

}
