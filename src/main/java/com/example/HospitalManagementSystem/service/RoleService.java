package com.example.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired 
	private RoleRepository roleRepository;
	
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}
}
