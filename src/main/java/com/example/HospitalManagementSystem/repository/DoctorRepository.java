package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
