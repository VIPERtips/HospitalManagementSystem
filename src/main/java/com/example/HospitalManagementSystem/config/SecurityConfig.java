package com.example.HospitalManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securitsyFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.authorizeHttpRequests(auth-> auth
						.requestMatchers("/api/login","/api/sign-up","/api/roles/**","/api/receptionists/**", "/api/nurses/**","/api/**","/api/patients/**").permitAll()
						//.requestMatchers("/api/nurses/**").hasAnyAuthority("NURSE","ADMIN")
						//.requestMatchers("/api/doctors/**","/api/patients/**","/api/appointments").hasAuthority("ADMIN"),"/api/nurses/"
						.anyRequest().authenticated()
						
						)
				.formLogin().disable()
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
