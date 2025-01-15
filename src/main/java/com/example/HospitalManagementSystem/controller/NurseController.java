package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.service.NurseService;

@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = "*")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @PostMapping
    public ResponseEntity<Nurse> createNurse(@RequestBody SignUpDto signUpDto) {
    	try {
            Nurse createdNurse = nurseService.createNurse(signUpDto);
            return ResponseEntity.ok(createdNurse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Nurse>> getAllNurses() {
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
    public ResponseEntity<Nurse> updateNurse(@PathVariable int id, @RequestBody Nurse updatedNurse) {
        try {
            return ResponseEntity.ok(nurseService.updateNurse(id, updatedNurse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
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
}