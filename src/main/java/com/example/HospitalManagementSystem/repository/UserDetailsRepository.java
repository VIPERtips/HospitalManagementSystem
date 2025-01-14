package com.example.HospitalManagementSystem.repository;

import com.example.HospitalManagementSystem.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
