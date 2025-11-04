package com.smartcity.entities.controller;

import com.smartcity.entities.dto.SensorDTO;
import com.smartcity.entities.service.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {
    
    private final SensorService sensorService;
    
    @PostMapping
    public ResponseEntity<SensorDTO> createSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        SensorDTO sensor = sensorService.createSensor(sensorDTO);
        return new ResponseEntity<>(sensor, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable String id) {
        SensorDTO sensor = sensorService.getSensorById(id);
        return ResponseEntity.ok(sensor);
    }
    
    @GetMapping
    public ResponseEntity<List<SensorDTO>> getAllSensors(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {
        List<SensorDTO> sensors;
        if (type != null) {
            sensors = sensorService.getSensorsByType(type);
        } else if (status != null) {
            sensors = sensorService.getSensorsByStatus(status);
        } else {
            sensors = sensorService.getAllSensors();
        }
        return ResponseEntity.ok(sensors);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable String id, 
                                                   @Valid @RequestBody SensorDTO sensorDTO) {
        SensorDTO sensor = sensorService.updateSensor(id, sensorDTO);
        return ResponseEntity.ok(sensor);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable String id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}

