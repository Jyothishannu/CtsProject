package com.fuel.project.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
 
import com.fuel.project.entity.Alert;
import com.fuel.project.entity.User;
import com.fuel.project.repository.AlertRepository;
import com.fuel.project.repository.UserRepository;
 
@Service
public class AlertService {
 
    @Autowired
    private AlertRepository alertRepository;
    
    @Autowired
    private UserRepository userRepository;
     
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
     
        return userRepository.findByUsername(username).get();
    }
 
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
    
    public Alert getAlertById(Long id) {
    	return alertRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Reading not found for id " + id));
    }

	public void deleteAllReadings() {
		alertRepository.deleteAll();
		
	}
}