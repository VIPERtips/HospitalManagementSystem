package com.example.HospitalManagementSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

	boolean existsByEmail(String email);

	Collection<UserDetails> findByFirstnameIgnoreCase(String nurseIdentifier);

}
