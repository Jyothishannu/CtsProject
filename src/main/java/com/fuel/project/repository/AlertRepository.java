package com.fuel.project.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import com.fuel.project.entity.Alert;
 
public interface AlertRepository extends JpaRepository<Alert, Long> {
}