package com.smartcity.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {
    private String id;
    
    @NotBlank(message = "Sensor name is required")
    private String name;
    
    @NotBlank(message = "Sensor type is required")
    private String type;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Latitude is required")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    private Double longitude;
    
    private String status;
    private String description;
    private String manufacturer;
    private String model;
    private LocalDateTime lastReading;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

