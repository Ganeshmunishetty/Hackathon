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
public class PublicAssetDTO {
    private String id;
    
    @NotBlank(message = "Asset name is required")
    private String name;
    
    @NotBlank(message = "Asset type is required")
    private String type;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Latitude is required")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    private Double longitude;
    
    private String status;
    private String description;
    private String department;
    private LocalDateTime lastMaintenance;
    private LocalDateTime nextMaintenance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

