package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	

}
