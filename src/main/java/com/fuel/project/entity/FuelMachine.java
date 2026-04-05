package com.fuel.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="fuel_machine")
public class FuelMachine {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long machineId;
	private String status;
	private LocalDateTime lastStarted;
	private LocalDateTime lastStopped;
	
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	
//	public FuelMachine() {}
//	
//	public FuelMachine(Long machineId, String status) {
//		super();
//		this.machineId = machineId;
//		this.status = status;
//	}
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
	
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
