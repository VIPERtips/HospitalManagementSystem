package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.ReceptionistRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

@Service
public class ReceptionistService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private RoleRepository roleRepository;

    
    public User createReceptionist(SignUpDto signUpDto) {
        Role receptionistRole = roleRepository.findByRole("RECEPTIONIST");
        if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            // Create user
            User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null,
                    receptionistRole);
            user = userRepository.save(user);

            // Create userDetails
            UserDetails userDetails = new UserDetails(signUpDto.getFirstname(), signUpDto.getLastname(),
                    signUpDto.getEmail(), signUpDto.getAddress(), signUpDto.getContact(), signUpDto.getDateOfBirth(),
                    signUpDto.getGender(), user);
            receptionistRepository.save(userDetails);
            user.setUserDetails(userDetails);

            // Save user again to link userDetails
            userRepository.save(user);

            return user;
        } else {
            throw new RuntimeException("Passwords do not match!");
        }
    }

    
    public List<UserDetails> getAllReceptionists() {
        return receptionistRepository.findAll();
    }

    
    public UserDetails getReceptionistById(int id) {
        Optional<UserDetails> optional = receptionistRepository.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("Receptionist not found with ID: " + id));
    }

    
    public UserDetails updateReceptionist(int id, UserDetails updatedDetails) {
        UserDetails existingDetails = getReceptionistById(id);

        existingDetails.setFirstname(updatedDetails.getFirstname());
        existingDetails.setLastname(updatedDetails.getLastname());
        existingDetails.setEmail(updatedDetails.getEmail());
        existingDetails.setAddress(updatedDetails.getAddress());
        existingDetails.setContact(updatedDetails.getContact());
        existingDetails.setDateOfBirth(updatedDetails.getDateOfBirth());
        existingDetails.setGender(updatedDetails.getGender());

        return receptionistRepository.save(existingDetails);
    }

   
    public void deleteReceptionist(int id) {
        UserDetails receptionist = getReceptionistById(id);
        receptionistRepository.delete(receptionist);
    }
}
