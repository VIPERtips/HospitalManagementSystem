package com.example.HospitalManagementSystem.controller;

import com.example.HospitalManagementSystem.model.Diagnosis;
import com.example.HospitalManagementSystem.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @PostMapping
    public Diagnosis createDiagnosis(@RequestBody Diagnosis diagnosis) {
        return diagnosisService.saveDiagnosis(diagnosis);
    }

    @GetMapping("/{id}")
    public Diagnosis getDiagnosis(@PathVariable int id) {
        return diagnosisService.findDiagnosisById(id);
    }
}
