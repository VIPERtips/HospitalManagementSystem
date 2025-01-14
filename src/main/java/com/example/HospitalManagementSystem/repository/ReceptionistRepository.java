package com.example.HospitalManagementSystem.repository;
import com.example.HospitalManagementSystem.model.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionistRepository extends JpaRepository<UserDetails, Integer> {
   
}
