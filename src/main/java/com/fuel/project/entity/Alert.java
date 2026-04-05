package com.fuel.project.entity;
 
import jakarta.persistence.*;
 
@Entity
@Table(name = "alert_table")
public class Alert {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;
 
    private Long generatorId;
 
    private Long levelreadingId;
 
    private String alertType;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
 
    public Long getAlertId() {
        return alertId;
    }
 
    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
 
    public Long getGeneratorId() {
        return generatorId;
    }
 
    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }
 
    public Long getLevelreadingId() {
        return levelreadingId;
    }
 
    public void setLevelreadingId(Long levelreadingId) {
        this.levelreadingId = levelreadingId;
    }
 
    public String getAlertType() {
        return alertType;
    }
 
    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}