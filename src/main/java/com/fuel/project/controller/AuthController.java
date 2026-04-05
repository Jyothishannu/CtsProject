package com.fuel.project.controller;
import com.fuel.project.dto.*;
import com.fuel.project.entity.User;
import com.fuel.project.repository.UserRepository;
import com.fuel.project.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/auth")
@CrossOrigin


public class AuthController {
	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
 
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // later use BCrypt
 
        userRepository.save(user);
 
        return "User Registered Successfully";
    }
 
    // LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
     
        System.out.println("LOGIN HIT");
     
        String token = jwtUtil.generateToken(request.getUsername());
     
        return new AuthResponse(token);
    }

}
