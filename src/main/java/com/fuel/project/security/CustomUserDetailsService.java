package com.fuel.project.security;
 
import com.fuel.project.entity.User;
import com.fuel.project.repository.UserRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
 
@Service
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
     
        System.out.println("Username from login: " + username);
     
        User user = userRepository.findByUsername(username);
     
        if (user == null) {
            System.out.println("User NOT FOUND in DB ❌");
            throw new UsernameNotFoundException("User not found");
        }
     
        System.out.println("User found: " + user.getUsername());
        System.out.println("Password from DB: " + user.getPassword());
     
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}