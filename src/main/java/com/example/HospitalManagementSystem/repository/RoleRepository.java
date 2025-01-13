package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HospitalManagementSystem.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	boolean existsByRole(String role);

}
