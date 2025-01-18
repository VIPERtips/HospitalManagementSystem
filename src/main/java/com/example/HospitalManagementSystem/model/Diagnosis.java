package com.example.HospitalManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

@Entity
public class Diagnosis {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Column(nullable = false)
	    private String diagnosisDetails;

	    @Column(nullable = false)
	    private String prescription;

	    @Column(nullable = false)
	    private int prescriptionDuration; 

	    public int getPrescriptionDuration() {
			return prescriptionDuration;
		}

		public void setPrescriptionDuration(int prescriptionDuration) {
			this.prescriptionDuration = prescriptionDuration;
		}

		@ManyToOne
	    @JoinColumn(name = "patient_id", nullable = false)
	    private Patient patient;

	    @ManyToOne
	    @JoinColumn(name = "doctor_id", nullable = false)
	    private Doctor doctor;


    public Diagnosis() {
        // Default constructor
    }

    public Diagnosis(String diagnosisDetails, String prescription, Patient patient, Doctor doctor,int prescriptionDuration) {
        this.diagnosisDetails = diagnosisDetails;
        this.prescription = prescription;
        this.patient = patient;
        this.doctor = doctor;
        this.prescriptionDuration = prescriptionDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosisDetails() {
        return diagnosisDetails;
    }

    public void setDiagnosisDetails(String diagnosisDetails) {
        this.diagnosisDetails = diagnosisDetails;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
