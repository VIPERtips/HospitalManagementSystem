package com.example.HospitalManagementSystem.repository;

import com.example.HospitalManagementSystem.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {
}