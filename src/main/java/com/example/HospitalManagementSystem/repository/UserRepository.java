package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.User;

public interface UserRepository  extends JpaRepository<User, Integer>{

	User findByUsername(String username);

	boolean existsByUsername(String email);

}
