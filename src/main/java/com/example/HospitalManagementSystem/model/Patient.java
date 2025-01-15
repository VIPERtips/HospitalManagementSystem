package com.example.HospitalManagementSystem.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @ManyToOne
    @JoinColumn(name = "userdetails_id")
    private UserDetails userDetails;
    

    @ManyToOne
    private Receptionist receptionist;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ConsultationFee> consultationFees;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public Patient() {
        // Default constructor
    }

    public Patient(UserDetails userDetails, User createdBy) {
        this.userDetails = userDetails;
        this.createdBy = createdBy;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
