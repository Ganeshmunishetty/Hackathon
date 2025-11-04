package com.smartcity.entities.service;

import com.smartcity.entities.dto.SensorDTO;
import com.smartcity.entities.entity.Sensor;
import com.smartcity.entities.exception.ResourceNotFoundException;
import com.smartcity.entities.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorService {
    
    private final SensorRepository sensorRepository;
    
    @Transactional
    public SensorDTO createSensor(SensorDTO sensorDTO) {
        Sensor sensor = mapToEntity(sensorDTO);
        Sensor saved = sensorRepository.save(sensor);
        return mapToDTO(saved);
    }
    
    public SensorDTO getSensorById(String id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + id));
        return mapToDTO(sensor);
    }
    
    public List<SensorDTO> getAllSensors() {
        return sensorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SensorDTO> getSensorsByType(String type) {
        return sensorRepository.findByType(type).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SensorDTO> getSensorsByStatus(String status) {
        Sensor.Status statusEnum = Sensor.Status.valueOf(status);
        return sensorRepository.findByStatus(statusEnum).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public SensorDTO updateSensor(String id, SensorDTO sensorDTO) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + id));
        
        updateEntityFromDTO(sensor, sensorDTO);
        Sensor saved = sensorRepository.save(sensor);
        return mapToDTO(saved);
    }
    
    @Transactional
    public void deleteSensor(String id) {
        if (!sensorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sensor not found with id: " + id);
        }
        sensorRepository.deleteById(id);
    }
    
    private Sensor mapToEntity(SensorDTO dto) {
        Sensor.SensorBuilder builder = Sensor.builder()
                .name(dto.getName())
                .type(dto.getType())
                .location(dto.getLocation())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .description(dto.getDescription())
                .manufacturer(dto.getManufacturer())
                .model(dto.getModel())
                .lastReading(dto.getLastReading());
        
        if (dto.getStatus() != null) {
            builder.status(Sensor.Status.valueOf(dto.getStatus()));
        } else {
            builder.status(Sensor.Status.ONLINE);
        }
        
        return builder.build();
    }
    
    private void updateEntityFromDTO(Sensor sensor, SensorDTO dto) {
        if (dto.getName() != null) sensor.setName(dto.getName());
        if (dto.getType() != null) sensor.setType(dto.getType());
        if (dto.getLocation() != null) sensor.setLocation(dto.getLocation());
        if (dto.getLatitude() != null) sensor.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) sensor.setLongitude(dto.getLongitude());
        if (dto.getStatus() != null) sensor.setStatus(Sensor.Status.valueOf(dto.getStatus()));
        if (dto.getDescription() != null) sensor.setDescription(dto.getDescription());
        if (dto.getManufacturer() != null) sensor.setManufacturer(dto.getManufacturer());
        if (dto.getModel() != null) sensor.setModel(dto.getModel());
        if (dto.getLastReading() != null) sensor.setLastReading(dto.getLastReading());
    }
    
    private SensorDTO mapToDTO(Sensor sensor) {
        return SensorDTO.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .type(sensor.getType())
                .location(sensor.getLocation())
                .latitude(sensor.getLatitude())
                .longitude(sensor.getLongitude())
                .status(sensor.getStatus().name())
                .description(sensor.getDescription())
                .manufacturer(sensor.getManufacturer())
                .model(sensor.getModel())
                .lastReading(sensor.getLastReading())
                .createdAt(sensor.getCreatedAt())
                .updatedAt(sensor.getUpdatedAt())
                .build();
    }
}

