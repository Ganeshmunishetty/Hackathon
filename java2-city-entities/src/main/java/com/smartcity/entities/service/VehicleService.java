package com.smartcity.entities.service;

import com.smartcity.entities.dto.VehicleDTO;
import com.smartcity.entities.entity.Vehicle;
import com.smartcity.entities.exception.BadRequestException;
import com.smartcity.entities.exception.ResourceNotFoundException;
import com.smartcity.entities.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    @Transactional
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        if (vehicleRepository.findByLicensePlate(vehicleDTO.getLicensePlate()).isPresent()) {
            throw new BadRequestException("Vehicle with license plate already exists");
        }
        
        Vehicle vehicle = mapToEntity(vehicleDTO);
        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToDTO(saved);
    }
    
    public VehicleDTO getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        return mapToDTO(vehicle);
    }
    
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<VehicleDTO> getVehiclesByType(String type) {
        return vehicleRepository.findByType(type).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public VehicleDTO updateVehicle(String id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        
        if (vehicleDTO.getLicensePlate() != null && 
            !vehicle.getLicensePlate().equals(vehicleDTO.getLicensePlate()) &&
            vehicleRepository.findByLicensePlate(vehicleDTO.getLicensePlate()).isPresent()) {
            throw new BadRequestException("License plate already exists");
        }
        
        updateEntityFromDTO(vehicle, vehicleDTO);
        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToDTO(saved);
    }
    
    @Transactional
    public VehicleDTO updateVehicleLocation(String id, Double latitude, Double longitude) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        
        vehicle.setCurrentLatitude(latitude);
        vehicle.setCurrentLongitude(longitude);
        vehicle.setLastUpdate(LocalDateTime.now());
        
        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToDTO(saved);
    }
    
    @Transactional
    public void deleteVehicle(String id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }
    
    private Vehicle mapToEntity(VehicleDTO dto) {
        Vehicle.VehicleBuilder builder = Vehicle.builder()
                .licensePlate(dto.getLicensePlate())
                .type(dto.getType())
                .department(dto.getDepartment())
                .currentLatitude(dto.getCurrentLatitude())
                .currentLongitude(dto.getCurrentLongitude())
                .driverName(dto.getDriverName())
                .driverId(dto.getDriverId())
                .description(dto.getDescription());
        
        if (dto.getStatus() != null) {
            builder.status(Vehicle.Status.valueOf(dto.getStatus()));
        } else {
            builder.status(Vehicle.Status.AVAILABLE);
        }
        
        return builder.build();
    }
    
    private void updateEntityFromDTO(Vehicle vehicle, VehicleDTO dto) {
        if (dto.getLicensePlate() != null) vehicle.setLicensePlate(dto.getLicensePlate());
        if (dto.getType() != null) vehicle.setType(dto.getType());
        if (dto.getDepartment() != null) vehicle.setDepartment(dto.getDepartment());
        if (dto.getStatus() != null) vehicle.setStatus(Vehicle.Status.valueOf(dto.getStatus()));
        if (dto.getCurrentLatitude() != null) vehicle.setCurrentLatitude(dto.getCurrentLatitude());
        if (dto.getCurrentLongitude() != null) vehicle.setCurrentLongitude(dto.getCurrentLongitude());
        if (dto.getDriverName() != null) vehicle.setDriverName(dto.getDriverName());
        if (dto.getDriverId() != null) vehicle.setDriverId(dto.getDriverId());
        if (dto.getDescription() != null) vehicle.setDescription(dto.getDescription());
        
        if (dto.getCurrentLatitude() != null || dto.getCurrentLongitude() != null) {
            vehicle.setLastUpdate(LocalDateTime.now());
        }
    }
    
    private VehicleDTO mapToDTO(Vehicle vehicle) {
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .type(vehicle.getType())
                .department(vehicle.getDepartment())
                .status(vehicle.getStatus().name())
                .currentLatitude(vehicle.getCurrentLatitude())
                .currentLongitude(vehicle.getCurrentLongitude())
                .driverName(vehicle.getDriverName())
                .driverId(vehicle.getDriverId())
                .lastUpdate(vehicle.getLastUpdate())
                .description(vehicle.getDescription())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}

