package com.example.HospitalManagementSystem.model;

import jakarta.persistence.Entity;

@Entity
public class Nurse extends UserDetails {

    public Nurse() {
        super();
    }

    public Nurse(String firstname, String lastname, String email, String address, String contact,
                 java.sql.Date dateOfBirth, String gender, User user) {
        super(firstname, lastname, email, address, contact, dateOfBirth, gender, user);
    }

}