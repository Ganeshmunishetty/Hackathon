package com.smartcity.entities.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private String id;
    
    @NotBlank(message = "License plate is required")
    private String licensePlate;
    
    @NotBlank(message = "Vehicle type is required")
    private String type;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    private String status;
    private Double currentLatitude;
    private Double currentLongitude;
    private String driverName;
    private String driverId;
    private LocalDateTime lastUpdate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

