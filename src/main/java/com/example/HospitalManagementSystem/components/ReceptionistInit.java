package com.example.HospitalManagementSystem.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;
import com.example.HospitalManagementSystem.service.EmailSender;
import com.example.HospitalManagementSystem.service.RoleService;

@Component
public class ReceptionistInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
    private EmailSender emailSender;

    @Override
    public void run(String... args) throws Exception {
        // Check if the Receptionist user already exists
        if (userRepository.findByUsername("lavetmbewe475@gmail.com") != null) {
            System.out.println("Receptionist user already exists. Skipping initialization.");
            return;
        }

        // Find the admin role
        Role ReceptionistRole = roleRepository.findByRole("Receptionist");
        
        
        if (ReceptionistRole == null) {
            ReceptionistRole = new Role("Receptionist", true);
            roleRepository.save(ReceptionistRole);  
            System.out.println("Created Receptionist role.");
        }

        // Create a new admin user
        User user = new User();
        user.setUsername("lavetmbewe475@gmail.com");
        user.setPassword(passwordEncoder.encode("2020"));
        user.setRole(ReceptionistRole);
        user.setUserDetails(null); 
        userRepository.save(user);
        
        String toEmail = user.getUsername();
        String subject = "Registration Email From I-Hub Hospital";
        String body = String.format("Dear Receptionist,\n\nWelcome to I-Hub Hospital! Here are your login credentials:\n\nUsername: %s\nPassword: %s\n\nPlease keep this information safe.\n\nBest regards,\nI-Hub Hospital Team",
                 user.getUsername(), user.getPassword()); 

        emailSender.sendEmail(toEmail, subject, body);

        System.out.println("Receptionist user created successfully.");
    }
}
