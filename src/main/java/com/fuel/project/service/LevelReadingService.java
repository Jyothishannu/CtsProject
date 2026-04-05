package com.fuel.project.service;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
 
import com.fuel.project.alertlogic.FuelAlertDetector;
import com.fuel.project.entity.Alert;
import com.fuel.project.entity.FuelMachine;
import com.fuel.project.entity.Generator;
import com.fuel.project.entity.LevelReading;
import com.fuel.project.entity.User;
import com.fuel.project.repository.AlertRepository;
import com.fuel.project.repository.GeneratorRepository;
import com.fuel.project.repository.LevelReadingRepository;
import com.fuel.project.repository.MachineRepository;
import com.fuel.project.repository.UserRepository;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
 
@Service
public class LevelReadingService {
 
    @Autowired
    private LevelReadingRepository levelReadingRepository;
    
    @Autowired
    private MachineRepository machineRepo;
 
    @Autowired
    private GeneratorRepository generatorRepository;
 
    @Autowired
    private AlertRepository alertRepository;
 
    @Autowired
    private FuelAlertDetector fuelAlertDetector;
    @ManyToOne
    @JoinColumn(name="generator_id")
    private Generator generator;
    
    @Autowired
    private UserRepository userRepository;
     
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
     
        return userRepository.findByUsername(username).get();
    }
 
    public LevelReading saveReading(LevelReading reading) {
    	 
        reading.setCurrentTimestamp(LocalDateTime.now());
     
        // get previous reading
        Optional<LevelReading> previousReading =
                levelReadingRepository.findTopByGeneratorIdOrderByCurrentTimestampDesc(
                        reading.getGeneratorId());
     
        Generator generator = generatorRepository
                .findById(reading.getGeneratorId())
                .orElseThrow();
     
        // allow only active generator
        if (!"ACTIVE".equals(generator.getStatus())) {
            throw new RuntimeException("Generator is inactive. Start it first");
        }
     
        // ✅ HANDLE FIRST RECORD
        float previousLevel = 0;
        LocalDateTime previousTime = null;
     
        if (previousReading.isPresent()) {
            previousLevel = previousReading.get().getCurrentFuellevel();
            previousTime = previousReading.get().getCurrentTimestamp();
        }
     
        float currentLevel = reading.getCurrentFuellevel();
     
        // ✅ CASE 1: FUEL INCREASE (Refill)
        if (currentLevel > previousLevel && previousReading.isPresent()) {
     
            FuelMachine machine = machineRepo
                    .findById(1L)
                    .orElseThrow();
     
            if (!"ACTIVE".equals(machine.getStatus())) {
                throw new RuntimeException(
                        "Fuel increase detected but Fuel Machine is OFF"
                );
            }
        }
     
        // SAVE READING
        LevelReading savedReading = levelReadingRepository.save(reading);
     
        // ALERT DETECTION
        String alertType = fuelAlertDetector.detectAlert(
                currentLevel,
                generator.getGeneratorHighLevelpoint(),
                generator.getGeneratorLowLevelpoint()
        );
     
        if (!alertType.equals("NORMAL")) {
            Alert alert = new Alert();
            alert.setGeneratorId(generator.getGeneratorId());
            alert.setLevelreadingId(savedReading.getLevelreadingId());
            alert.setAlertType(alertType);
     
            alertRepository.save(alert);
        }
     
        // ✅ SUDDEN DROP LOGIC (ONLY IF PREVIOUS EXISTS)
        if (previousReading.isPresent()) {
            LocalDateTime currentTime = reading.getCurrentTimestamp();
     
            long timeDiff = java.time.Duration
                    .between(previousTime, currentTime)
                    .toSeconds();

            if (timeDiff > 0) {

                float levelDiff = previousLevel - currentLevel;
                float currentRate = levelDiff / timeDiff;
     
                float tankCapacity = generator.getGeneratorTotalCapacity();
                
                double a = (tankCapacity / 1000.0);
                
                double b = (500.0 / 3600.0);
                
                double normalRate = a * b;
                
                if (currentRate > normalRate) {
                    Alert alert = new Alert();
                    alert.setGeneratorId(generator.getGeneratorId());
                    alert.setLevelreadingId(savedReading.getLevelreadingId());
                    alert.setAlertType("SUDDEN_DROP_ALERT");
     
                    alertRepository.save(alert);
                }
            }
        }
     
        return savedReading;
    }

     

    
    public List<LevelReading> getAllLevelReadings() {
        return levelReadingRepository.findAll();
    }
    
    public LevelReading getLevelReadingById(Long id) {
        return levelReadingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading not found for id " + id));
    }
    public void deleteAllReadings() {
    	levelReadingRepository.deleteAll();
    }
    public void deleteById(Long id) {
    	levelReadingRepository.deleteById(id);
    }
}