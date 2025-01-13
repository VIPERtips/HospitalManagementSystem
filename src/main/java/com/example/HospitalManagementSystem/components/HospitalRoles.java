package com.example.HospitalManagementSystem.components;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.repository.RoleRepository;


@Component
public class HospitalRoles implements CommandLineRunner{
	@Autowired
	private RoleRepository rolerepository;

	@Override
	public void run(String... args) throws Exception {
		List<String> roles = Arrays.asList(
				"Doctor",
				"Patient",
				"Pharmacist",
				"Admin"
				
				);
		roles.forEach(role->{
			if(!rolerepository.existsByRole(role)) {
				Role roleType = new Role();
				roleType.setRole(role);
				rolerepository.save(roleType);
			}
			
		});
		
	}

}

