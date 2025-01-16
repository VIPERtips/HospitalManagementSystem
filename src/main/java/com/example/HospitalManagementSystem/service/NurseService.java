package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.PatientVitals;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.NurseRepository;
import com.example.HospitalManagementSystem.repository.PatientRepository;
import com.example.HospitalManagementSystem.repository.PatientVitalsRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserDetailsRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class NurseService {
	
	@Autowired
	private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
     private PatientVitalsRepository patientVitalsRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Transactional
    public User createNurse(SignUpDto signUpDto) {
        Role nurseRole = roleRepository.findByRole("NURSE");
        if (nurseRole == null) {
            throw new RuntimeException("Role NURSE not found!");
        }

        if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            
            User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null, nurseRole);
            user = userRepository.save(user);

            
            UserDetails userDetails = new UserDetails(signUpDto.getFirstname(), signUpDto.getLastname(),
                    signUpDto.getEmail(), signUpDto.getAddress(), signUpDto.getContact(),
                    signUpDto.getDateOfBirth(), signUpDto.getGender(), user);

            userDetailsRepository.save(userDetails);
            user.setUserDetails(userDetails); 

            
            userRepository.save(user);

            
            Nurse nurse = new Nurse(userDetails);
            nurseRepository.save(nurse);

            return user; 
        } else {
            throw new RuntimeException("Passwords do not match!");
        }
    }

    public List<UserDetails> getAllNurses() {
        
        Role nurseRole = roleRepository.findByRole("NURSE");
        
       
        return userDetailsRepository.findAll().stream()
                .filter(nurse -> nurse.getUser().getRole().equals(nurseRole)) 
                .collect(Collectors.toList());
    }



    public Nurse getNurseById(int id) {
        Optional<Nurse> optional = nurseRepository.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("Nurse not found with ID: " + id));
    }

    public Nurse updateNurse(int id, SignUpDto updatedDetails) {
        
        Nurse existingNurse = nurseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nurse not found with ID: " + id));

       
        UserDetails existingUserDetails = existingNurse.getUserDetails();

        
        existingUserDetails.setFirstname(updatedDetails.getFirstname());
        existingUserDetails.setLastname(updatedDetails.getLastname());
        existingUserDetails.setEmail(updatedDetails.getEmail());
        existingUserDetails.setAddress(updatedDetails.getAddress());
        existingUserDetails.setContact(updatedDetails.getContact());
        existingUserDetails.setDateOfBirth(updatedDetails.getDateOfBirth());
        existingUserDetails.setGender(updatedDetails.getGender());

        
        userDetailsRepository.save(existingUserDetails);

        
        existingNurse.setUserDetails(existingUserDetails);

        
        nurseRepository.save(existingNurse);

        return existingNurse; 
    }

    public void deleteNurse(int id) {
        Nurse nurse = getNurseById(id);
        nurseRepository.delete(nurse);
    }
    
    
    public void recordPatientVitals(int patientId, PatientVitals vitals) {
       
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

       
        vitals.setPatient(patient);
        patientVitalsRepository.save(vitals);
    }

    public List<PatientVitals> getPatientVitals(int patientId) {
        
        patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

        
        return patientVitalsRepository.findByPatientId(patientId);
    }
    /*
    public List<Patient> getPatientsAssignedToNurse(int nurseId) {
        
        Optional<Nurse> optionalNurse = nurseRepository.findById(nurseId);
        Nurse nurse = optionalNurse.orElseThrow(() -> new RuntimeException("Nurse not found."));

        
        List<Patient> patients = patientRepository.findByNurse(nurse); 

        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found for this nurse.");
        }

        return patients;
    }*/  public List<Patient> getPatientsAssignedToLoggedInNurse() {
        // Retrieve the logged-in nurse's username from the session
        String username = (String) session.getAttribute("username");

        if (username == null) {
            throw new RuntimeException("No authenticated user found!");
        }

        // Find the nurse using the username of the logged-in nurse
        User nurseUser = userRepository.findByUsername(username);
        Nurse nurse = nurseRepository.findByUserDetails(nurseUser.getUserDetails())
                                     .orElseThrow(() -> new RuntimeException("Nurse not found."));

        // Get patients assigned to this nurse
        List<Patient> patients = patientRepository.findByNurse(nurse);

        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found for this nurse.");
        }

        return patients;
    }


}