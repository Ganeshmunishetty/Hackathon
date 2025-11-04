package com.smartcity.events.service;

import com.smartcity.events.dto.EventDTO;
import com.smartcity.events.dto.NormalizedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventNormalizationService {
    
    public NormalizedEvent normalize(EventDTO eventDTO) {
        Map<String, Object> normalizedData = new HashMap<>();
        
        // Normalize event type
        String normalizedType = normalizeEventType(eventDTO.getType());
        
        // Normalize severity
        String normalizedSeverity = normalizeSeverity(eventDTO.getSeverity());
        
        // Normalize status
        String normalizedStatus = normalizeStatus(eventDTO.getStatus());
        
        // Add metadata
        normalizedData.put("originalType", eventDTO.getType());
        normalizedData.put("source", eventDTO.getSource());
        normalizedData.put("location", eventDTO.getLocation());
        if (eventDTO.getMetadata() != null) {
            normalizedData.putAll(eventDTO.getMetadata());
        }
        
        return NormalizedEvent.builder()
                .eventId(eventDTO.getId())
                .eventType(normalizedType)
                .source(eventDTO.getSource())
                .severity(normalizedSeverity)
                .status(normalizedStatus)
                .location(eventDTO.getLocation())
                .latitude(eventDTO.getLatitude())
                .longitude(eventDTO.getLongitude())
                .description(eventDTO.getDescription())
                .assignedTo(eventDTO.getAssignedTo())
                .eventTimestamp(eventDTO.getTimestamp() != null ? eventDTO.getTimestamp() : LocalDateTime.now())
                .normalizedData(normalizedData)
                .processedAt(LocalDateTime.now())
                .version("1.0")
                .build();
    }
    
    private String normalizeEventType(String type) {
        if (type == null) return "unknown";
        
        return switch (type.toLowerCase()) {
            case "traffic", "traffic_jam", "congestion" -> "traffic";
            case "emergency", "fire", "medical", "police" -> "emergency";
            case "pollution", "air_quality", "noise" -> "pollution";
            case "infrastructure", "maintenance", "utility" -> "infrastructure";
            default -> type.toLowerCase();
        };
    }
    
    private String normalizeSeverity(String severity) {
        if (severity == null) return "low";
        
        return switch (severity.toLowerCase()) {
            case "critical", "urgent" -> "critical";
            case "high", "major" -> "high";
            case "medium", "moderate" -> "medium";
            case "low", "minor" -> "low";
            default -> severity.toLowerCase();
        };
    }
    
    private String normalizeStatus(String status) {
        if (status == null) return "open";
        
        return switch (status.toLowerCase()) {
            case "open", "new", "pending" -> "open";
            case "in-progress", "in_progress", "processing" -> "in-progress";
            case "resolved", "closed", "completed" -> "resolved";
            default -> status.toLowerCase();
        };
    }
}

