package com.example.HospitalManagementSystem.repository;

import com.example.HospitalManagementSystem.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {
}