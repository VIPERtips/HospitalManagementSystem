package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    
}
