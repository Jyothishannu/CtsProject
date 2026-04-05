package com.fuel.project.entity;
 
import java.time.LocalDateTime;

import jakarta.persistence.*;
 
@Entity
@Table(name = "generator_table")
public class Generator {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long generatorId;
 
    private float generatorTotalCapacity;
    private float generatorHighLevelpoint;
    private float generatorLowLevelpoint;
    
    private String status;
    private LocalDateTime lastStarted;
    private LocalDateTime lastStopped;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    public User getUser() {
    	return user;
    }
    public void setUser(User user) {
    	this.user=user;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public void setStatus(String status) {
    	this.status=status;
    }
    
    public LocalDateTime getLastStarted() {
    	return lastStarted;
    }
    
    public void setLastStarted(LocalDateTime lastStarted) {
    	this.lastStarted=lastStarted;
    }
    
    public LocalDateTime getLastStopped() {
    	return lastStopped;
    }
    
    public void setLastStopped(LocalDateTime lastStopped) {
    	this.lastStopped=lastStopped;
    }
 
    public Long getGeneratorId() {
        return generatorId;
    }
 
    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }
 
    public float getGeneratorTotalCapacity() {
        return generatorTotalCapacity;
    }
 
    public void setGeneratorTotalCapacity(float generatorTotalCapacity) {
        this.generatorTotalCapacity = generatorTotalCapacity;
    }
 
    public float getGeneratorHighLevelpoint() {
        return generatorHighLevelpoint;
    }
 
    public void setGeneratorHighLevelpoint(float generatorHighLevelpoint) {
        this.generatorHighLevelpoint = generatorHighLevelpoint;
    }
 
    public float getGeneratorLowLevelpoint() {
        return generatorLowLevelpoint;
    }
 
    public void setGeneratorLowLevelpoint(float generatorLowLevelpoint) {
        this.generatorLowLevelpoint = generatorLowLevelpoint;
    }
}