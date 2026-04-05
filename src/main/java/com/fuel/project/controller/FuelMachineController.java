package com.fuel.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuel.project.entity.Alert;
import com.fuel.project.entity.FuelMachine;
import com.fuel.project.service.FuelMachineService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/machines")
public class FuelMachineController {
	
	@Autowired
	FuelMachineService machineService;

	@PostMapping
	public FuelMachine createMachine(@RequestBody FuelMachine fuelMachine) {
		return machineService.saveMachine(fuelMachine);
	}
	
	@PutMapping("/on/{id}")
	public FuelMachine start(@PathVariable Long id) {
		return machineService.startMachine(id);
	}
	
	@PutMapping("/off/{id}")
	public FuelMachine stop(@PathVariable Long id) {
		return machineService.stopMachine(id);
	}
	

}
