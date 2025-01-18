package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

}
