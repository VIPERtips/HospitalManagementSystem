package com.example.HospitalManagementSystem.repository;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.UserDetails;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {
    Optional<Nurse> findById(int id);
    
    
    Optional<Nurse> findByUserDetails_FirstnameIgnoreCase(String firstname);


	Optional<Nurse> findByUserDetails(UserDetails userDetails);
}
