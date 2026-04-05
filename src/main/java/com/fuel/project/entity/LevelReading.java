package com.fuel.project.entity;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "level_reading")
public class LevelReading {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelreadingId;
 
    private Long generatorId;
 
    private float currentFuellevel;
    @Column(name = "`current_timestamp`")
    private LocalDateTime currentTimestamp;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
 
    public Long getLevelreadingId() {
        return levelreadingId;
    }
 
    public void setLevelreadingId(Long levelreadingId) {
        this.levelreadingId = levelreadingId;
    }
 
    public Long getGeneratorId() {
        return generatorId;
    }
 
    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }
 
    public float getCurrentFuellevel() {
        return currentFuellevel;
    }
 
    public void setCurrentFuellevel(float currentFuellevel) {
        this.currentFuellevel = currentFuellevel;
    }
 
    public LocalDateTime getCurrentTimestamp() {
        return currentTimestamp;
    }
 
    public void setCurrentTimestamp(LocalDateTime currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }
}