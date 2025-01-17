package com.example.HospitalManagementSystem.service;

import com.example.HospitalManagementSystem.model.Diagnosis;
import com.example.HospitalManagementSystem.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Diagnosis saveDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public Diagnosis findDiagnosisById(int id) {
        return diagnosisRepository.findById(id).orElseThrow(() -> new RuntimeException("Diagnosis not found"));
    }
}
