package com.example.HospitalManagementSystem.config;

import com.example.HospitalManagementSystem.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/sign-up", "/api/roles/**", "/api/receptionists/**", 
                                 "/api/nurses/**", "/api/**", "/api/patients/**").permitAll()
                .requestMatchers("/api/receptionists/patients/register").hasRole("RECEPTIONIST")

                .anyRequest().authenticated()
            )
            .formLogin().disable()
            .addFilterBefore(new JWTAuthenticationFilter(authenticationManager(http)), 
                             UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthorizationFilter(customUserDetailsService), 
                             UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
