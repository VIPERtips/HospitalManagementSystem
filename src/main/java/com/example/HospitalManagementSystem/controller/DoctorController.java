package com.example.HospitalManagementSystem.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HospitalManagementSystem.model.Diagnosis;
import com.example.HospitalManagementSystem.model.Doctor;
import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Prescription;
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
	
	@PostMapping("/patients/{patientId}/diagnoses")
    public ResponseEntity<Object> createDiagnosis(
            @PathVariable int patientId,
            @RequestParam int doctorId,
            @RequestBody Map<String, Object> payload) {
        try {
            String diagnosisDetails = (String) payload.get("diagnosisDetails");
            String prescription = (String) payload.get("prescription");
            int duration = (int) payload.get("duration");

            Diagnosis diagnosis = doctorService.createDiagnosis(patientId, doctorId, diagnosisDetails, prescription, duration);
            return ResponseEntity.ok(diagnosis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/diagnoses/{diagnosisId}")
    public ResponseEntity<Object> updateDiagnosis(
            @PathVariable int diagnosisId,
            @RequestBody Map<String, Object> payload) {
        try {
            String diagnosisDetails = (String) payload.get("diagnosisDetails");
            String prescription = (String) payload.get("prescription");
            int duration = (int) payload.get("duration");

            Diagnosis updatedDiagnosis = doctorService.updateDiagnosis(diagnosisId, diagnosisDetails, prescription, duration);
            return ResponseEntity.ok(updatedDiagnosis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/diagnoses/{diagnosisId}")
    public ResponseEntity<String> deleteDiagnosis(@PathVariable int diagnosisId) {
        try {
            doctorService.deleteDiagnosis(diagnosisId);
            return ResponseEntity.ok("Diagnosis deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/patients/{patientId}/prescriptions")
    public ResponseEntity<Object> sendPrescriptionToReceptionist(
            @PathVariable int patientId,
            @RequestParam int receptionistId,
            @RequestBody Map<String, String> payload) {
        try {
            String instructions = payload.get("instructions");
            Prescription prescription = doctorService.sendPrescriptionToReceptionist(patientId, receptionistId, instructions);
            return ResponseEntity.ok(prescription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
