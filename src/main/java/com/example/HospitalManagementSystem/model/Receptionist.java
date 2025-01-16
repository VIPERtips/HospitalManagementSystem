package com.example.HospitalManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Receptionist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @ManyToOne
    @JoinColumn(name = "userdetails_id")
    private UserDetails userDetails;
     public Receptionist() {
		// TODO Auto-generated constructor stub
	}
     
     
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	public Receptionist(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
     
    
}
