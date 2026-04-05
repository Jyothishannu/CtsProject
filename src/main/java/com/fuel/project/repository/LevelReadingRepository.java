package com.fuel.project.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fuel.project.entity.LevelReading;

public interface LevelReadingRepository extends JpaRepository<LevelReading, Long> {
	 
    Optional<LevelReading> findTopByGeneratorIdOrderByCurrentTimestampDesc(Long generatorId);
 
}