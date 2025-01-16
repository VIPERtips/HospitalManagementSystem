package com.example.HospitalManagementSystem.model;

import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private UserDetails userDetails;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	private String authToken;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, UserDetails userDetails,
	Role role,String token) {
		this.username = username;
		this.password = password;
		this.userDetails = userDetails;
		this.role = role;
		this.authToken = token;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
