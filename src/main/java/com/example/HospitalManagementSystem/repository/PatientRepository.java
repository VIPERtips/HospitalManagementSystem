package com.example.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	List<Patient> findByNurse(Nurse nurse);

	

}
