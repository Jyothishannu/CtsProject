package com.fuel.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fuel.project.entity.FuelMachine;

public interface MachineRepository extends JpaRepository<FuelMachine, Long>{


}
