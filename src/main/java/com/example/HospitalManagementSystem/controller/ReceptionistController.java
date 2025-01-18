package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.HospitalManagementSystem.model.ConsultationFee;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.service.ReceptionistService;

@RestController
@RequestMapping("/api/receptionists")
@CrossOrigin(origins = "*")
public class ReceptionistController {

    @Autowired
    private ReceptionistService receptionistService;

   
    @PostMapping
    public ResponseEntity<?> createReceptionist(@RequestBody SignUpDto signUpDto) {
        try {
            return ResponseEntity.ok(receptionistService.createReceptionist(signUpDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping
    public ResponseEntity<List<UserDetails>> getAllReceptionists() {
        return ResponseEntity.ok(receptionistService.getAllReceptionists());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getReceptionistById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(receptionistService.getReceptionistById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReceptionist(@PathVariable int id, @RequestBody UserDetails updatedDetails) {
        try {
            return ResponseEntity.ok(receptionistService.updateReceptionist(id, updatedDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReceptionist(@PathVariable int id) {
        try {
            receptionistService.deleteReceptionist(id);
            return ResponseEntity.ok("Receptionist deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/patients/register")
    public ResponseEntity<String> registerPatient(@RequestBody SignUpDto signUpDto, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            
            String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : null;

            
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Missing or invalid token");
            }

            
            receptionistService.registerPatientForReceptionist(signUpDto, token);

            return ResponseEntity.ok("Patient registration successful!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        try {
            Patient patient = receptionistService.findPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    
    @PostMapping("/patients/{id}/charge-consultation-fee")
    public ResponseEntity<String> chargeConsultationFee(@PathVariable int id, @RequestParam double feeAmount) {
        try {
            Patient patient = receptionistService.findPatientById(id);
            ConsultationFee consultationFee = receptionistService.chargeConsultationFee(patient, feeAmount);
            return ResponseEntity.ok("Consultation fee charged: " + consultationFee.getFee());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/patients/{id}/send-to-nurse")
    public ResponseEntity<String> sendPatientDetailsToNurse(@PathVariable int id, @RequestBody int nurseIdentifier) {
        try {
            receptionistService.sendPatientDetailsToNurse(id, nurseIdentifier);
            return ResponseEntity.ok("Patient details sent to the nurse.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = receptionistService.getAllPatientsWithDetails();
        return ResponseEntity.ok(patients);
    }

    
}
