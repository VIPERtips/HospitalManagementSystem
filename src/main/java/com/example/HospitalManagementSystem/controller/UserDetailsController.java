package com.example.HospitalManagementSystem.controller;

import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userdetails")
public class UserDetailsController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @PostMapping
    public UserDetails createUserDetails(@RequestBody UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
}
