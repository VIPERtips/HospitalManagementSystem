package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.service.PatientService;


@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@GetMapping
	public ResponseEntity<List<UserDetails>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
		try {
			Patient patient = patientService.getPatientBy(id);
			return ResponseEntity.ok(patient);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updatePatient(@PathVariable int id, @RequestBody SignUpDto updatedPatient) {
		try {
			return ResponseEntity.ok(patientService.updatePatient(id, updatedPatient));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletPatient(@PathVariable int id) {
		try {
			patientService.deletePatient(id);
			return ResponseEntity.ok("Nurse deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
		
	
	}
	
}
