package com.example.HospitalManagementSystem.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
public class PatientVitals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double temperature;  
    private Double weight;       
    private String bloodPressure; 
    private Integer heartRate;   
    private Date date;
   

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    @ManyToOne  // **Added relationship to Nurse**
    @JoinColumn(name = "nurse_id") // **Nurse ID column**
    private Nurse nurse;
    
    @ManyToOne  // **Added relationship to Doctor**
    @JoinColumn(name = "doctor_id") // **Doctor ID column**
    private Doctor doctor; // **Field for Doctor reference**


    public PatientVitals() {
		// TODO Auto-generated constructor stub
	}
    
    public PatientVitals(Double temperature, Double weight, String bloodPressure, Integer heartRate, Patient patient) {
		this.temperature = temperature;
		this.weight = weight;
		this.bloodPressure = bloodPressure;
		this.heartRate = heartRate;
		this.patient = patient;
		this.nurse = nurse; // **Initialize the nurse**
        this.doctor = doctor; // **Initialize the doctor**
		this.date = new Date(System.currentTimeMillis());
	}
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	    public Doctor getDoctor() { // **Getter for Doctor**
	        return doctor;  
	    }

	    public void setDoctor(Doctor doctor) { // **Setter for Doctor**
	        this.doctor = doctor;  
	    }
	    
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

