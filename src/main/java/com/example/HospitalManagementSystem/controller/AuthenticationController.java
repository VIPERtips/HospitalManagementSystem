package com.example.HospitalManagementSystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.HospitalManagementSystem.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
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
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
		try {
			authenticationService.registerUser(signUpDto);
			return ResponseEntity.ok("Sign up successful. Log in credentials sent to your email!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User user, HttpSession session) {
		String username = user.getUsername();
		String password = user.getPassword();
		User existingUser = userService.findByUsername(username);

		if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
			session.setAttribute("username", username);
			String token = JWTUtil.generateToken(username);
			existingUser.setAuthToken(token);
			return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(existingUser);
		} else {
			return ResponseEntity.badRequest().body("Invalid credentials");
		}
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
	    String refreshToken = request.getHeader("Authorization");

	    if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
	        refreshToken = refreshToken.substring(7); 

	        try {
	            
	            if (JWTUtil.isTokenExpired(refreshToken)) {
	                String username = JWTUtil.getUsername(refreshToken);

	                
	                String newAccessToken = JWTUtil.generateToken(username);

	                Map<String, String> tokens = new HashMap<>();
	                tokens.put("accessToken", newAccessToken);
	                tokens.put("refreshToken", refreshToken);

	                return ResponseEntity.ok(tokens);
	            } else {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error processing refresh token.");
	        }
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing.");
	}


	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		try {
			session.invalidate(); 
			return ResponseEntity.ok("Logout successful.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error during logout: " + e.getMessage());
		}
	}
}
