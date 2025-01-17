package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Patient;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.UserDetails;

import com.example.HospitalManagementSystem.repository.PatientRepository;
import com.example.HospitalManagementSystem.repository.PatientVitalsRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserDetailsRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

@Service
public class PatientService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	

	@Autowired
	private PatientVitalsRepository patientVitalsRepository;

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public List<UserDetails> getAllPatients(){
		Role patientRole = roleRepository.findByRole("PATIENT");
		return userDetailsRepository.findAll().stream().filter(patient -> patient.getUser().getRole().equals(patientRole))
				.collect(Collectors.toList());
	}
	
	public Patient getPatientBy(int id) {
		Optional<Patient> optional = patientRepository.findById(id);
		return optional.orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
	}
	
	public Patient updatePatient(int id, SignUpDto updatedDetails) {

		Patient existingNurse = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));

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

		patientRepository.save(existingNurse);

		return existingNurse;
	}

	public void deletePatient(int id) {
		Patient patient = getPatientBy(id);
		patientRepository.delete(patient);
	}
}
