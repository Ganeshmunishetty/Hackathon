package com.smartcity.events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private String id;
    
    @NotBlank(message = "Event type is required")
    private String type; // traffic, emergency, pollution, infrastructure
    
    @NotBlank(message = "Event source is required")
    private String source; // sensor, camera, manual, system
    
    @NotBlank(message = "Severity is required")
    private String severity; // low, medium, high, critical
    
    @NotBlank(message = "Status is required")
    private String status; // open, in-progress, resolved
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Latitude is required")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    private Double longitude;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    private String assignedTo;
    private LocalDateTime timestamp;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
}

