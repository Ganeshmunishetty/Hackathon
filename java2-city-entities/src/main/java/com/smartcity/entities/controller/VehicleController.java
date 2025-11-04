package com.smartcity.entities.controller;

import com.smartcity.entities.dto.VehicleDTO;
import com.smartcity.entities.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO vehicle = vehicleService.createVehicle(vehicleDTO);
        return ResponseEntity.ok(vehicle);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable String id) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }
    
    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles(
            @RequestParam(required = false) String type) {
        List<VehicleDTO> vehicles;
        if (type != null) {
            vehicles = vehicleService.getVehiclesByType(type);
        } else {
            vehicles = vehicleService.getAllVehicles();
        }
        return ResponseEntity.ok(vehicles);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable String id, 
                                                    @Valid @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO vehicle = vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.ok(vehicle);
    }
    
    @PatchMapping("/{id}/location")
    public ResponseEntity<VehicleDTO> updateVehicleLocation(
            @PathVariable String id,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        VehicleDTO vehicle = vehicleService.updateVehicleLocation(id, latitude, longitude);
        return ResponseEntity.ok(vehicle);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}

