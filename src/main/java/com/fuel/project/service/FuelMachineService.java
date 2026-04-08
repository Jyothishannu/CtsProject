package com.fuel.project.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fuel.project.entity.Alert;
import com.fuel.project.entity.FuelMachine;
import com.fuel.project.entity.Generator;
import com.fuel.project.entity.User;
import com.fuel.project.repository.MachineRepository;
import com.fuel.project.repository.UserRepository;

@Service
public class FuelMachineService {

	@Autowired
	private MachineRepository machineRepo;
	
	@Autowired
    private UserRepository userRepository;
     
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
     
        return userRepository.findByUsername(username);
    }
	
	//start the machine(ON)
	public FuelMachine startMachine(Long id) {
		FuelMachine fuelMachine = machineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Generator not found"));
		
		fuelMachine.setStatus("ACTIVE");
		fuelMachine.setLastStarted(LocalDateTime.now());
		
		return machineRepo.save(fuelMachine);
		
	}
	
	//stop the machine(OFF)
	public FuelMachine stopMachine(Long id) {
		FuelMachine fuelMachine= machineRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Machine not found"));
		
		fuelMachine.setStatus("INACTIVE");
		fuelMachine.setLastStopped(LocalDateTime.now());
		
		return machineRepo.save(fuelMachine);
		
		
	}
	
	public FuelMachine saveMachine(FuelMachine fuelMachine) {
		return machineRepo.save(fuelMachine);
	}
	
	 public FuelMachine getMachineById(Long id) {
	    	return machineRepo.findById(id)
	    			.orElseThrow(() -> new RuntimeException("Reading not found for id " + id));
	    }
}
