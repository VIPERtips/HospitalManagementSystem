package com.example.HospitalManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.model.UserDetails;
import com.example.HospitalManagementSystem.repository.PatientRepository;
import com.example.HospitalManagementSystem.repository.RoleRepository;
import com.example.HospitalManagementSystem.repository.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	private EmailSender emailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private RoleRepository roleRepository;

	public User registerUser(SignUpDto signUpDto) {
		Role patientRole = roleRepository.findByRole("Patient");
		if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
			// store user
			User user = new User(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), null,
					patientRole);
			// save the user
			user = userRepository.save(user);

			// store userDetails
			UserDetails userDetails = new UserDetails(signUpDto.getFirstname(), signUpDto.getLastname(),
					signUpDto.getEmail(), signUpDto.getAddress(), signUpDto.getContact(), signUpDto.getDateOfBirth(),
					signUpDto.getGender(), user);
			patientRepository.save(userDetails);
			user.setUserDetails(userDetails);
			//save user again
			userRepository.save(user);
			//sending email
			String toEmail = userDetails.getEmail();
	        String subject = "Registration Email From I-Hub Hospital";
	        String body = String.format("Dear %s,\n\nWelcome to I-Hub Hospital! Here are your login credentials:\n\nUsername: %s\nPassword: %s\n\nPlease keep this information safe.\n\nBest regards,\nI-Hub Hospital Team",
	                userDetails.getFirstname(), user.getUsername(), signUpDto.getPassword()); 

	        emailSender.sendEmail(toEmail, subject, body);
			
			return user;
		} else {
			throw new RuntimeException("Sign up failed check your input fields!");
		}
	}
}
