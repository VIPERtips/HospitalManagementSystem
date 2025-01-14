package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HospitalManagementSystem.config.SpringSecurityUser;
import com.example.HospitalManagementSystem.model.Role;
import com.example.HospitalManagementSystem.model.SignUpDto;
import com.example.HospitalManagementSystem.model.User;
import com.example.HospitalManagementSystem.service.AuthenticationService;
import com.example.HospitalManagementSystem.service.RoleService;
import com.example.HospitalManagementSystem.service.UserService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){
		try {
			authenticationService.registerUser(signUpDto);
			return ResponseEntity.ok("Sign up successful. Log in credentials sent to your email!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	/*@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user,HttpSession session){
		String username = user.getUsername();
		String password = user.getPassword();
		User existingUser = userService.findByUsername(username);
		if(existingUser!=null && passwordEncoder.matches(password, existingUser.getPassword())) {
			session.setAttribute(username, existingUser);
			return ResponseEntity.ok(("Login successfull"));
		}else {
			 return ResponseEntity.badRequest().body("Invalid credentials");
		}
		
	}*/
	
	 @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody User user, HttpSession session) {
	        String username = user.getUsername();
	        String password = user.getPassword();
	        User existingUser = userService.findByUsername(username);

	        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
	            // Create SpringSecurityUser object
	            SpringSecurityUser springSecurityUser = new SpringSecurityUser(
	                    existingUser.getUsername(),
	                    existingUser.getPassword(),
	                    existingUser.getRole().getAuthority() 
	            );

	            // Store in the session
	            session.setAttribute("loggedInUser", springSecurityUser);

	            return ResponseEntity.ok("Login successful");
	        } else {
	            return ResponseEntity.badRequest().body("Invalid credentials");
	        }
	    }
	

}
