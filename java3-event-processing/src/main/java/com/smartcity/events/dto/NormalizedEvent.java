package com.smartcity.events.dto;

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
public class NormalizedEvent {
    private String eventId;
    private String eventType;
    private String source;
    private String severity;
    private String status;
    private String location;
    private Double latitude;
    private Double longitude;
    private String description;
    private String assignedTo;
    private LocalDateTime eventTimestamp;
    private Map<String, Object> normalizedData;
    private LocalDateTime processedAt;
    private String version;
}

