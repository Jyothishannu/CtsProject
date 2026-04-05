package com.fuel.project.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fuel.project.entity.Generator;
import com.fuel.project.entity.User;
 
public interface GeneratorRepository extends JpaRepository<Generator, Long> {
	List<Generator> findByUser(User user);
}