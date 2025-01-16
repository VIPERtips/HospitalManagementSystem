package com.example.HospitalManagementSystem.model;

public class JwtResponse {

    private String authToken;
    private Role role;
    private UserDetails userDetails;

    public JwtResponse(String authToken, Role role, UserDetails userDetails) {
        this.authToken = authToken;
        this.role = role;
        this.userDetails = userDetails;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
