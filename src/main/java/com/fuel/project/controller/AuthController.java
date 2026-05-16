package com.fuel.project.controller;
import com.fuel.project.dto.*;

import com.fuel.project.entity.User;
import com.fuel.project.repository.UserRepository;
import com.fuel.project.security.JwtUtil;
import com.fuel.project.service.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;

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
    
    @Autowired
    private TokenBlacklistService blacklistService;
 
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
    
    //LOGOUT
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            blacklistService.tokenBlacklist(token);

            return "Logged out successfully";
        }

        return "No token found";
    }

}
