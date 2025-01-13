package com.example.HospitalManagementSystem.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String role;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}

