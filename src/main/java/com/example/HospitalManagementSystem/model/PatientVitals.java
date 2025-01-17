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

    public PatientVitals() {
		// TODO Auto-generated constructor stub
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

	public PatientVitals(Double temperature, Double weight, String bloodPressure, Integer heartRate, Patient patient) {
		this.temperature = temperature;
		this.weight = weight;
		this.bloodPressure = bloodPressure;
		this.heartRate = heartRate;
		this.patient = patient;
		this.date = new Date(System.currentTimeMillis());
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

