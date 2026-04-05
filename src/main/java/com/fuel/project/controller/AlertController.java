package com.fuel.project.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
import com.fuel.project.entity.Alert;
import com.fuel.project.service.AlertService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/alerts")
public class AlertController {
 
    @Autowired
    private AlertService alertService;
 
    @GetMapping
    public List<Alert> getAlerts() {
        return alertService.getAllAlerts();
    }
    
    @GetMapping("/{id}")
    public Alert getReadingById(@PathVariable Long id) {
        return alertService.getAlertById(id);
    }
    @DeleteMapping
    public void deleteAllReadings() {
    	alertService.deleteAllReadings();
    }
}