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
@Table(name = "public_assets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PublicAsset {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String type; // streetlight, traffic_light, water_pump, power_station, etc.
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // OPERATIONAL, MAINTENANCE, OUT_OF_SERVICE
    
    @Column
    private String description;
    
    @Column
    private String department; // Traffic, Utilities, Environmental, etc.
    
    @Column
    private LocalDateTime lastMaintenance;
    
    @Column
    private LocalDateTime nextMaintenance;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum Status {
        OPERATIONAL, MAINTENANCE, OUT_OF_SERVICE
    }
}

