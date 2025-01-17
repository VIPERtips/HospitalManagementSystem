package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.Doctor;
import com.example.HospitalManagementSystem.model.Nurse;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.DoctorRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserDetailsRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

@Service
public class DoctorService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public User createDoctor(SignUpDto signUpDto) {
		Role doctorRole = roleRepository.findByRole("DOCTOR");
		if (doctorRole == null) {
			throw new RuntimeException("Role DOCTOR not found");
		}
		if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
			User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null,
					doctorRole, null);
			user = userRepository.save(user);
			
			UserDetails userDetails = new UserDetails(signUpDto.getFirstname(), signUpDto.getLastname(),
					signUpDto.getEmail(), signUpDto.getAddress(), signUpDto.getContact(), signUpDto.getDateOfBirth(),
					signUpDto.getGender(), user);
			
			userDetailsRepository.save(userDetails);
			user.setUserDetails(userDetails);
			userRepository.save(user);
			
			Doctor doctor = new Doctor(userDetails);
			doctorRepository.save(doctor);
			
			return user;
		} else {
			throw new RuntimeException("Passwords do not match!");
		}

	}
	
	public List<UserDetails> getAllDoctors() {

		Role doctorRole = roleRepository.findByRole("DOCTOR");

		return userDetailsRepository.findAll().stream().filter(doctor -> doctor.getUser().getRole().equals(doctorRole))
				.collect(Collectors.toList());
	}
	
	public Doctor getDoctorById(int id) {
		Optional<Doctor> optional = doctorRepository.findById(id);
		return optional.orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
	}
	
	public Doctor updateNurse(int id, SignUpDto updatedDetails) {

		Doctor existingDoctor = doctorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

		UserDetails existingUserDetails = existingDoctor.getUserDetails();

		existingUserDetails.setFirstname(updatedDetails.getFirstname());
		existingUserDetails.setLastname(updatedDetails.getLastname());
		existingUserDetails.setEmail(updatedDetails.getEmail());
		existingUserDetails.setAddress(updatedDetails.getAddress());
		existingUserDetails.setContact(updatedDetails.getContact());
		existingUserDetails.setDateOfBirth(updatedDetails.getDateOfBirth());
		existingUserDetails.setGender(updatedDetails.getGender());

		userDetailsRepository.save(existingUserDetails);

		existingDoctor.setUserDetails(existingUserDetails);

		doctorRepository.save(existingDoctor);

		return existingDoctor;
	}
	
	public void deleteDoctor(int id) {
		Doctor doctor = getDoctorById(id);
		doctorRepository.delete(doctor);
	}

}
