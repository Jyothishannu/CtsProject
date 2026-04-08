package com.fuel.project.service;
 
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
 
import com.fuel.project.entity.Generator;
import com.fuel.project.entity.User;
import com.fuel.project.repository.GeneratorRepository;
import com.fuel.project.repository.UserRepository;

@Service
public class GeneratorService {
 
    @Autowired
    private GeneratorRepository generatorRepository;
    
    @Autowired
    private UserRepository userRepository;
     
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
     
        return userRepository.findByUsername(username);
    }
    
    // START GENERATOR
    public Generator startGenerator(Long id) {
        Generator generator = generatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generator not found"));
 
        generator.setStatus("ACTIVE");
        generator.setLastStarted(LocalDateTime.now());
 
        return generatorRepository.save(generator);
    }
    
    //STOP GENERATOR
    public Generator stopGenerator(Long id) {
    	Generator generator = generatorRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Generator not found"));
    	
    	generator.setStatus("INACTIVE");
    	generator.setLastStopped(LocalDateTime.now());
    	
    	return generatorRepository.save(generator);
    }
 
    public Generator saveGenerator(Generator generator) {
    	User user=getLoggedInUser();
    	generator.setUser(user);
        return generatorRepository.save(generator);
    }
 
    public List<Generator> getAllGenerators() {
    	User user=getLoggedInUser();
        return generatorRepository.findByUser(user);
    }
    
    public Generator getGeneratorById(Long id) {
        return generatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading not found for id " + id));
    }
}