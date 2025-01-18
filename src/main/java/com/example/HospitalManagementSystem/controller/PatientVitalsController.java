package com.example.HospitalManagementSystem.controller;

import com.example.HospitalManagementSystem.model.PatientVitals;
import com.example.HospitalManagementSystem.service.PatientVitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vital-signs")
@CrossOrigin(origins = "*")
public class PatientVitalsController {
	@Autowired
	private PatientVitalsService patientVitalsService;

	@PostMapping
	public ResponseEntity<PatientVitals> addVitalSign(@RequestBody PatientVitals patientVitals) {
		PatientVitals savedVitals = patientVitalsService.save(patientVitals);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedVitals);
	}

	@GetMapping("/patients/{patientId}")
	public ResponseEntity<List<PatientVitals>> getVitalSignsForPatient(@PathVariable int patientId) {
		List<PatientVitals> vitalSigns = patientVitalsService.findByPatientId(patientId);
		return ResponseEntity.ok(vitalSigns);
	}

	
}