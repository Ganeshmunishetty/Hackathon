package com.smartcity.events.controller;

import com.smartcity.events.dto.EventDTO;
import com.smartcity.events.service.EventIngestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    
    private final EventIngestionService ingestionService;
    
    @PostMapping("/ingest")
    public ResponseEntity<EventDTO> ingestEvent(@Valid @RequestBody EventDTO eventDTO) {
        EventDTO processedEvent = ingestionService.ingestEvent(eventDTO);
        return new ResponseEntity<>(processedEvent, HttpStatus.CREATED);
    }
    
    @PostMapping("/batch")
    public ResponseEntity<String> ingestBatchEvents(@Valid @RequestBody java.util.List<EventDTO> events) {
        for (EventDTO event : events) {
            ingestionService.ingestEvent(event);
        }
        return ResponseEntity.ok("Batch processed: " + events.size() + " events");
    }
}

