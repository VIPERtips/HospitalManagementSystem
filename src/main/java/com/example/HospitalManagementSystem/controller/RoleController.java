package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<Role>> getRoles(){
		return ResponseEntity.ok(roleService.findAllRoles());
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<Object> getRoleByName(@PathVariable String name){
		try {
			
			return ResponseEntity.ok(roleService.findRoleByName(name));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> createRole(@RequestBody Role role){
		try {
			return ResponseEntity.ok(roleService.createRole(role));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to create role: "+e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateRole(@PathVariable int id,@RequestBody Role role){
		try {
			Role existingRole = roleService.findById(id);
			if(existingRole != null) {
				existingRole.setRole(role.getRole());
				existingRole.setActive(role.isActive());
				return ResponseEntity.ok(roleService.updateRole(existingRole));
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to update role: "+e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable int id){
		try {
			Role existingRole = roleService.findById(id);
			if(existingRole != null) {
				roleService.deleteRole(id);
				return ResponseEntity.ok("Role deleted successfully");
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to delete role: "+e.getMessage());
		}
	}
}
