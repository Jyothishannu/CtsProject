package com.fuel.project.controller;
import com.fuel.project.dto.*;

import com.fuel.project.entity.User;
import com.fuel.project.repository.UserRepository;
import com.fuel.project.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
 
 
@RestController
@RequestMapping("/auth")
@CrossOrigin


public class AuthController {
	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
     
        User user = new User();
        user.setUsername(request.getUsername());
     
        // 🔥 MUST encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
     
        userRepository.save(user);
     
        return "User registered successfully";
    }
 
    // LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
     
        // 🔥 THIS LINE IS MANDATORY
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
     
        String token = jwtUtil.generateToken(request.getUsername());
     
        return new AuthResponse(token);
    }

}
