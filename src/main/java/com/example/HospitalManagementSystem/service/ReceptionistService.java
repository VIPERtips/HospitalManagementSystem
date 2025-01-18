package com.example.HospitalManagementSystem.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.config.SpringSecurityUser;
import com.example.HospitalManagementSystem.model.ConsultationFee;
import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.Receptionist;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.ConsultationFeeRepository;
import com.example.HospitalManagementSystem.repository.NurseRepository;
import com.example.HospitalManagementSystem.repository.PatientRepository;
import com.example.HospitalManagementSystem.repository.ReceptionistRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserDetailsRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;
import com.example.HospitalManagementSystem.util.JWTUtil;

import jakarta.servlet.http.HttpSession;


@Service
public class ReceptionistService {
	@Autowired
	private HttpSession session;
	
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
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    
    @Autowired
    private ConsultationFeeRepository consultationFeeRepository;

    
    @Autowired
    private NurseRepository nurseRepository;
    
    public User createReceptionist(SignUpDto signUpDto) {
        Role receptionistRole = roleRepository.findByRole("RECEPTIONIST");
        if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            // Create user
            User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null,
                    receptionistRole,null);
            user = userRepository.save(user);

            // Create userDetails
            UserDetails userDetails = new UserDetails(signUpDto.getFirstname(), signUpDto.getLastname(),
                    signUpDto.getEmail(), signUpDto.getAddress(), signUpDto.getContact(), signUpDto.getDateOfBirth(),
                    signUpDto.getGender(), user);
            userDetailsRepository.save(userDetails);
            user.setUserDetails(userDetails);

            // Save user again to link userDetails
            userRepository.save(user);
            
            // Create and link Receptionist to UserDetails
            Receptionist receptionist = new Receptionist(); 
            receptionist.setUserDetails(userDetails);
            receptionistRepository.save(receptionist); 
            
            
            System.out.println("Receptionist ID: " + receptionist.getId());
            System.out.println("User ID: " + user.getId());


            return user;
        } else {
            throw new RuntimeException("Passwords do not match!");
        }
    }
    
    public List<UserDetails> getAllReceptionists() {
        Role receptionistRole = roleRepository.findByRole("RECEPTIONIST");
        return userDetailsRepository.findAll().stream()
                .filter(userDetails -> userDetails.getUser().getRole().equals(receptionistRole))
                .collect(Collectors.toList());
    }


    
    public Receptionist getReceptionistById(int id) {
        Optional<Receptionist> optional = receptionistRepository.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("Receptionist not found with ID: " + id));
    }

    
    public Receptionist updateReceptionist(int id, UserDetails updatedDetails) {
        
        Receptionist existingReceptionist = getReceptionistById(id);

       
        UserDetails existingDetails = existingReceptionist.getUserDetails();

        existingDetails.setFirstname(updatedDetails.getFirstname());
        existingDetails.setLastname(updatedDetails.getLastname());
        existingDetails.setEmail(updatedDetails.getEmail());
        existingDetails.setAddress(updatedDetails.getAddress());
        existingDetails.setContact(updatedDetails.getContact());
        existingDetails.setDateOfBirth(updatedDetails.getDateOfBirth());
        existingDetails.setGender(updatedDetails.getGender());

        // Save the updated UserDetails entity
        userDetailsRepository.save(existingDetails);

        
        receptionistRepository.save(existingReceptionist);

        // Return the updated Receptionist object
        return existingReceptionist;
    }


   
    public void deleteReceptionist(int id) {
        Receptionist receptionist = getReceptionistById(id);
        receptionistRepository.delete(receptionist);
    }
    
   
    public User registerPatientForReceptionist(SignUpDto signUpDto, String token) {
        String username = JWTUtil.getUsername(token);

        // Token verification and username extraction
        if (username == null) {
            throw new RuntimeException("No authenticated user found!");
        }

        User receptionist = userRepository.findByUsername(username);
        if (receptionist == null) {
            throw new RuntimeException("Receptionist not found with the provided username.");
        }

        // Get the "Patient" role
        Role patientRole = roleRepository.findByRole("Patient");
        if (patientRole == null) {
            throw new RuntimeException("Role 'Patient' does not exist.");
        }

        // Check if the email already exists
        if (userRepository.existsByUsername(signUpDto.getEmail())) {
            throw new RuntimeException("Email already exists. Please use a different email address.");
        }

        // Check if the passwords match
        if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            // Create and save the new user
            User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null, patientRole, null);
            user = userRepository.save(user);

            // Create and save user details
            UserDetails userDetails = new UserDetails(
                signUpDto.getFirstname(),
                signUpDto.getLastname(),
                signUpDto.getEmail(),
                signUpDto.getAddress(),
                signUpDto.getContact(),
                signUpDto.getDateOfBirth(),
                signUpDto.getGender(),
                user
            );
            userDetails = userDetailsRepository.save(userDetails);

            // Create and save patient
            Patient patient = new Patient(userDetails, receptionist);
            patientRepository.save(patient);

            // Update user with user details
            user.setUserDetails(userDetails);
            userRepository.save(user);

            // Send registration email
            String toEmail = userDetails.getEmail();
            String subject = "Registration Email From I-Hub Hospital";
            String body = String.format("Dear %s,\n\nWelcome to I-Hub Hospital! Here are your login credentials:\n\nUsername: %s\nPassword: %s\n\nPlease keep this information safe.\n\nBest regards,\nI-Hub Hospital Team",
                    userDetails.getFirstname(), user.getUsername(), signUpDto.getPassword());

            emailSender.sendEmail(toEmail, subject, body);

            return user;
        } else {
            throw new RuntimeException("Sign up failed: Passwords do not match!");
        }
    }

    
    public Patient findPatientById(int id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }
    
    public ConsultationFee chargeConsultationFee(Patient patient, double feeAmount) {
        
        ConsultationFee consultationFee = new ConsultationFee(patient, feeAmount);
        consultationFeeRepository.save(consultationFee);

        
        patient.setConsultationFeePaid(true);
        patientRepository.save(patient);

        return consultationFee;
    }
    
    public void sendPatientDetailsToNurse(int patientId, int nurseIdentifier) {
        
        Patient patient = findPatientById(patientId);

        
        if (!patient.isConsultationFeePaid()) {
            throw new RuntimeException("Consultation fee has not been paid for this patient.");
        }

        
        Optional<Nurse> optionalNurse = nurseRepository.findById(nurseIdentifier);

       
        Nurse nurse = optionalNurse.orElseThrow(() -> new RuntimeException("Nurse not found."));

        
        String role = nurse.getUserDetails().getUser().getRole().getRole();
        if (!role.equalsIgnoreCase("NURSE")) {
            throw new RuntimeException("The specified user is not a nurse.");
        }

        
        patient.setNurse(nurse);
        patientRepository.save(patient);

       
        System.out.println("Patient details sent to Nurse: " + nurse.getUserDetails().getFirstname() + " " + nurse.getUserDetails().getLastname());
    }
       
    public List<Patient> getAllPatientsWithDetails() {
        // Fetch the "Patient" role from the repository
        Role patientRole = roleRepository.findByRole("Patient");

       
        if (patientRole == null) {
            System.out.println("Patient role not found!");
            return new ArrayList<>();
        }

       
        return patientRepository.findAll().stream()
                .filter(patient -> {
                    
                    UserDetails userDetails = patient.getUserDetails();
                    if (userDetails != null && userDetails.getUser() != null) {
                        String userRole = userDetails.getUser().getRole().getRole();
                        return "Patient".equalsIgnoreCase(userRole); 
                    }
                    return false;
                })
                .peek(patient -> {
                    
                    UserDetails userDetails = patient.getUserDetails();
                    if (userDetails != null) {
                        System.out.println("Patient Name: " + userDetails.getFirstname() + " " + userDetails.getLastname());
                    }

                    
                    if (patient.getCreatedBy() != null && patient.getCreatedBy().getUserDetails() != null) {
                        System.out.println("Created By (Receptionist): " + patient.getCreatedBy().getUserDetails().getFirstname());
                    }
                    if (patient.getNurse() != null && patient.getNurse().getUserDetails() != null) {
                        System.out.println("Assigned Nurse: " + patient.getNurse().getUserDetails().getFirstname());
                    }
                    if (patient.getConsultationFees() != null) {
                        System.out.println("Consultation Fee: " + patient.getConsultationFees());
                    }
                })
                .collect(Collectors.toList());
    }





}