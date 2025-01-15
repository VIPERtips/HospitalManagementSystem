package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.repository.NurseRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

@Service
public class NurseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Nurse createNurse(SignUpDto signUpDto) {
        Role nurseRole = roleRepository.findByRole("NURSE");
        if (nurseRole == null) {
            throw new RuntimeException("Role NURSE not found!");
        }

        if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            // Create user
            User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null, nurseRole);
            user = userRepository.save(user);

            // Create nurse details
            Nurse nurseDetails = new Nurse();
            nurseDetails.setFirstname(signUpDto.getFirstname());
            nurseDetails.setLastname(signUpDto.getLastname());
            nurseDetails.setEmail(signUpDto.getEmail());
            nurseDetails.setAddress(signUpDto.getAddress());
            nurseDetails.setContact(signUpDto.getContact());
            nurseDetails.setDateOfBirth(signUpDto.getDateOfBirth());
            nurseDetails.setGender(signUpDto.getGender());
            nurseDetails.setUser(user);

            nurseRepository.save(nurseDetails);
            user.setUserDetails(nurseDetails);

            // Save user again to link userDetails
            userRepository.save(user);

            return nurseDetails; // Return the Nurse object
        } else {
            throw new RuntimeException("Passwords do not match!");
        }
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public Nurse getNurseById(int id) {
        Optional<Nurse> optional = nurseRepository.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("Nurse not found with ID: " + id));
    }

    public Nurse updateNurse(int id, Nurse updatedDetails) {
        Nurse existingDetails = getNurseById(id);

        existingDetails.setFirstname(updatedDetails.getFirstname());
        existingDetails.setLastname(updatedDetails.getLastname());
        existingDetails.setEmail(updatedDetails.getEmail());
        existingDetails.setAddress(updatedDetails.getAddress());
        existingDetails.setContact(updatedDetails.getContact());
        existingDetails.setDateOfBirth(updatedDetails.getDateOfBirth());
        existingDetails.setGender(updatedDetails.getGender());

        return nurseRepository.save(existingDetails);
    }

    public void deleteNurse(int id) {
        Nurse nurse = getNurseById(id);
        nurseRepository.delete(nurse);
    }
}