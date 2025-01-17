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

import com.example.HospitalManagementSystem.model.Doctor;
import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@PostMapping
	public ResponseEntity<Object> createDoctor(@RequestBody SignUpDto signUpDto) {
		try {
			
			return ResponseEntity.ok(doctorService.createDoctor(signUpDto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<UserDetails>> getAllDoctors() {
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
		try {
			Doctor doctor = doctorService.getDoctorById(id);
			return ResponseEntity.ok(doctor);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateDoctor(@PathVariable int id, @RequestBody SignUpDto updatedDoctor) {
		try {
			return ResponseEntity.ok(doctorService.updateNurse(id, updatedDoctor));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
		try {
			doctorService.deleteDoctor(id);
			return ResponseEntity.ok("Doctor deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}
}
