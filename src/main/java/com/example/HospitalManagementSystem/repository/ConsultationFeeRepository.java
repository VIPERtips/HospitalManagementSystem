package com.example.HospitalManagementSystem.repository;

import com.example.HospitalManagementSystem.model.ConsultationFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationFeeRepository extends JpaRepository<ConsultationFee, Integer> {
    
}
