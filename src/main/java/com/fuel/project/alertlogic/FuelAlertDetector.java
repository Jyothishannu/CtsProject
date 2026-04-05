package com.fuel.project.alertlogic;
 
import org.springframework.stereotype.Component;
 
@Component
public class FuelAlertDetector {
 
    public String detectAlert(float currentLevel, float highLevel, float lowLevel) {
 
        if (currentLevel < lowLevel) {  
            return "LOW_LEVEL_ALERT";
        }
 
        if (currentLevel > highLevel) {
            return "HIGH_LEVEL_ALERT";
        }
 
        return "NORMAL";
    }
}