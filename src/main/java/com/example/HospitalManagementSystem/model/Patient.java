package com.example.HospitalManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Patient extends UserDetails {
    
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public Patient() {
        // Default constructor
    }

    
    public Patient(String firstname, String lastname, String email, String address, String contact, java.sql.Date dateOfBirth, String gender, User createdBy) {
        super(firstname, lastname, email, address, contact, dateOfBirth, gender, createdBy);
        this.createdBy = createdBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
