package com.example.HospitalManagementSystem.controller;

import com.example.HospitalManagementSystem.model.Diagnosis;
import com.example.HospitalManagementSystem.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnoses")
@CrossOrigin(origins = "*")
public class DiagnosisController {

	@Autowired
	private DiagnosisService diagnosisService;

	@PostMapping
	public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) {
		return ResponseEntity.ok(diagnosisService.saveDiagnosis(diagnosis));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getDiagnosis(@PathVariable int id) {

		try {
			Diagnosis diagnosis = diagnosisService.findDiagnosisById(id);

			return ResponseEntity.ok(diagnosis);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
