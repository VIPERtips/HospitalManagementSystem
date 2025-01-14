package com.example.HospitalManagementSystem.repository;

import com.example.HospitalManagementSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
