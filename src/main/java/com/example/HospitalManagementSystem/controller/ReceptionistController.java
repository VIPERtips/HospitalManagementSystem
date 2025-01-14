package com.example.HospitalManagementSystem.controller;

import com.example.HospitalManagementSystem.model.Receptionist;
import com.example.HospitalManagementSystem.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receptionists")
public class ReceptionistController {

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @PostMapping
    public Receptionist createReceptionist(@RequestBody Receptionist receptionist) {
        return receptionistRepository.save(receptionist);
    }
}