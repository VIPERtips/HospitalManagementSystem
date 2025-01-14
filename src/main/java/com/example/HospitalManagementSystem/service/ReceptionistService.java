package com.example.HospitalManagementSystem.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.config.SpringSecurityUser;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.PatientRepository;
import com.example.HospitalManagementSystem.repository.ReceptionistRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

@Service
public class ReceptionistService {
	@Autowired
    private EmailSender emailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    
    @Autowired
    private PatientRepository patientRepository;

    
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
    
   
    public User registerPatientForReceptionist(SignUpDto signUpDto) {
        // Retrieve the current authenticated user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getEmail();  
        } else if (principal instanceof String) {
            username = (String) principal; 
        }

        if (username == null) {
            throw new RuntimeException("No authenticated user found!");
        }

       
        User receptionist = userRepository.findByUsername(username);

        Role patientRole = roleRepository.findByRole("Patient");

        if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            
            User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null, patientRole);

            
            user = userRepository.save(user);

            
            UserDetails patientUserDetails = new UserDetails(
                    signUpDto.getFirstname(),
                    signUpDto.getLastname(),
                    signUpDto.getEmail(),
                    signUpDto.getAddress(),
                    signUpDto.getContact(),
                    signUpDto.getDateOfBirth(),
                    signUpDto.getGender(),
                    user
            );

            // Save the patient user details
            patientRepository.save(patientUserDetails);

            // Step 4: Link UserDetails to User and save again
            user.setUserDetails(patientUserDetails);
            userRepository.save(user);

            // Step 5: Create the Patient entity and link the receptionist as the creator
            Patient patient = new Patient(
                    signUpDto.getFirstname(),
                    signUpDto.getLastname(),
                    signUpDto.getEmail(),
                    signUpDto.getAddress(),
                    signUpDto.getContact(),
                    signUpDto.getDateOfBirth(),
                    signUpDto.getGender(),
                    receptionist // Set the receptionist who created this patient
            );
            patientRepository.save(patient);

            // Step 6: Send the registration email to the patient
            String toEmail = patientUserDetails.getEmail();
            String subject = "Registration Email From I-Hub Hospital";
            String body = String.format("Dear %s,\n\nWelcome to I-Hub Hospital! Here are your login credentials:\n\nUsername: %s\nPassword: %s\n\nPlease keep this information safe.\n\nBest regards,\nI-Hub Hospital Team",
                    patientUserDetails.getFirstname(), user.getUsername(), signUpDto.getPassword());

            emailSender.sendEmail(toEmail, subject, body);

            // Return the created user
            return user;
        } else {
            throw new RuntimeException("Sign up failed: check your input fields!");
        }
    }

}
