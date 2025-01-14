package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

}
