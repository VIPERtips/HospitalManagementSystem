package com.example.HospitalManagementSystem.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.PatientVitals;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.service.NurseService;

@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = "*")
public class NurseController {

	@Autowired
	private NurseService nurseService;

	@PostMapping
	public ResponseEntity<Object> createNurse(@RequestBody SignUpDto signUpDto) {
		try {
			
			return ResponseEntity.ok(nurseService.createNurse(signUpDto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<UserDetails>> getAllNurses() {
		return ResponseEntity.ok(nurseService.getAllNurses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Nurse> getNurseById(@PathVariable int id) {
		try {
			Nurse nurse = nurseService.getNurseById(id);
			return ResponseEntity.ok(nurse);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateNurse(@PathVariable int id, @RequestBody SignUpDto updatedNurse) {
		try {
			return ResponseEntity.ok(nurseService.updateNurse(id, updatedNurse));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteNurse(@PathVariable int id) {
		try {
			nurseService.deleteNurse(id);
			return ResponseEntity.ok("Nurse deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}
	
	 @PostMapping("/patients/{patientId}/vitals")
	    public ResponseEntity<String> recordPatientVitals(@PathVariable int patientId, @RequestBody PatientVitals vitals) {
	        try {
	            nurseService.recordPatientVitals(patientId, vitals);
	            return ResponseEntity.ok("Vitals recorded successfully for Patient ID: " + patientId);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
	        }
	    }
	 
	 @GetMapping("/patients/{patientId}/vitals")
	    public ResponseEntity<?> getPatientVitals(@PathVariable int patientId) {
	        try {
	            return ResponseEntity.ok(nurseService.getPatientVitals(patientId));
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
	        }
	    }
	 
	 /*@GetMapping("/{nurseId}/patients")
	 public ResponseEntity<List<Patient>> getPatientsAssignedToNurse(@PathVariable int nurseId) {
	     try {
	         List<Patient> patients = nurseService.getPatientsAssignedToNurse(nurseId);
	         return ResponseEntity.ok(patients);
	     } catch (RuntimeException e) {
	         return ResponseEntity.badRequest().body(Collections.emptyList()); 
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	     }
	 }*/
	 
	 @GetMapping("/patients")
	    public ResponseEntity<List<Patient>> getPatientsAssignedToLoggedInNurse() {
	        try {
	            List<Patient> patients = nurseService.getPatientsAssignedToLoggedInNurse();
	            return ResponseEntity.ok(patients);
	        } catch (RuntimeException e) {
	            return ResponseEntity.badRequest().body(Collections.emptyList());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	        }
	    }

}
