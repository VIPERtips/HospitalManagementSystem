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
	
	public Role createRole(Role role) {
		if(!roleRepository.existsByRole(role.getRole())) {
			return roleRepository.save(role);
		}else {
			throw new RuntimeException("Duplicate role entered for "+role.getRole());
		}
	}
	
	public void deleteRole(int id) {
		if(roleRepository.existsById(id)) {
			roleRepository.deleteById(id);
		} else {
			throw new RuntimeException("No role with id : "+id+ " found.");
		}
	}
	
	public Role updateRole(Role role) {
		Role exisistingRole = roleRepository.findById(role.getId()).orElse(null);
		if(exisistingRole !=null) {
			exisistingRole.setRole(role.getRole());
			exisistingRole.setActive(role.isActive());
			return roleRepository.save(exisistingRole);
		} else {
			throw new RuntimeException("Role doesn't exists");
		}
	}
	
	public Role findRoleByName(String roleName) {
		Role role =  roleRepository.findByRole(roleName);
		if (role != null) {
			return role;
		} else {
			throw new RuntimeException("Role is not found");
		}
	}

	public Role findById(int id) {
		return roleRepository.findById(id).orElse(null);
	}
}
