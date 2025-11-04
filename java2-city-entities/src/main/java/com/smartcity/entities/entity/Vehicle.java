package com.smartcity.entities.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, unique = true)
    private String licensePlate;
    
    @Column(nullable = false)
    private String type; // patrol, ambulance, fire_truck, maintenance, etc.
    
    @Column(nullable = false)
    private String department; // Police, Fire, Traffic, Utilities, etc.
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // AVAILABLE, IN_USE, MAINTENANCE, OUT_OF_SERVICE
    
    @Column
    private Double currentLatitude;
    
    @Column
    private Double currentLongitude;
    
    @Column
    private String driverName;
    
    @Column
    private String driverId;
    
    @Column
    private LocalDateTime lastUpdate;
    
    @Column
    private String description;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum Status {
        AVAILABLE, IN_USE, MAINTENANCE, OUT_OF_SERVICE
    }
}

