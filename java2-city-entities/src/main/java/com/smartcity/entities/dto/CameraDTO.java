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
public class CameraDTO {
    private String id;
    
    @NotBlank(message = "Camera name is required")
    private String name;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Latitude is required")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    private Double longitude;
    
    private String status;
    private String streamUrl;
    private String ipAddress;
    private Integer port;
    private Integer incidentCount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

