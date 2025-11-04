package com.smartcity.entities.service;

import com.smartcity.entities.dto.CameraDTO;
import com.smartcity.entities.entity.Camera;
import com.smartcity.entities.exception.ResourceNotFoundException;
import com.smartcity.entities.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CameraService {
    
    private final CameraRepository cameraRepository;
    
    @Transactional
    public CameraDTO createCamera(CameraDTO cameraDTO) {
        Camera camera = mapToEntity(cameraDTO);
        Camera saved = cameraRepository.save(camera);
        return mapToDTO(saved);
    }
    
    public CameraDTO getCameraById(String id) {
        Camera camera = cameraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camera not found with id: " + id));
        return mapToDTO(camera);
    }
    
    public List<CameraDTO> getAllCameras() {
        return cameraRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CameraDTO> getCamerasByStatus(String status) {
        Camera.Status statusEnum = Camera.Status.valueOf(status);
        return cameraRepository.findByStatus(statusEnum).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CameraDTO updateCamera(String id, CameraDTO cameraDTO) {
        Camera camera = cameraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camera not found with id: " + id));
        
        updateEntityFromDTO(camera, cameraDTO);
        Camera saved = cameraRepository.save(camera);
        return mapToDTO(saved);
    }
    
    @Transactional
    public void deleteCamera(String id) {
        if (!cameraRepository.existsById(id)) {
            throw new ResourceNotFoundException("Camera not found with id: " + id);
        }
        cameraRepository.deleteById(id);
    }
    
    private Camera mapToEntity(CameraDTO dto) {
        Camera.CameraBuilder builder = Camera.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .streamUrl(dto.getStreamUrl())
                .ipAddress(dto.getIpAddress())
                .port(dto.getPort())
                .incidentCount(dto.getIncidentCount() != null ? dto.getIncidentCount() : 0)
                .description(dto.getDescription());
        
        if (dto.getStatus() != null) {
            builder.status(Camera.Status.valueOf(dto.getStatus()));
        } else {
            builder.status(Camera.Status.ONLINE);
        }
        
        return builder.build();
    }
    
    private void updateEntityFromDTO(Camera camera, CameraDTO dto) {
        if (dto.getName() != null) camera.setName(dto.getName());
        if (dto.getLocation() != null) camera.setLocation(dto.getLocation());
        if (dto.getLatitude() != null) camera.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) camera.setLongitude(dto.getLongitude());
        if (dto.getStatus() != null) camera.setStatus(Camera.Status.valueOf(dto.getStatus()));
        if (dto.getStreamUrl() != null) camera.setStreamUrl(dto.getStreamUrl());
        if (dto.getIpAddress() != null) camera.setIpAddress(dto.getIpAddress());
        if (dto.getPort() != null) camera.setPort(dto.getPort());
        if (dto.getIncidentCount() != null) camera.setIncidentCount(dto.getIncidentCount());
        if (dto.getDescription() != null) camera.setDescription(dto.getDescription());
    }
    
    private CameraDTO mapToDTO(Camera camera) {
        return CameraDTO.builder()
                .id(camera.getId())
                .name(camera.getName())
                .location(camera.getLocation())
                .latitude(camera.getLatitude())
                .longitude(camera.getLongitude())
                .status(camera.getStatus().name())
                .streamUrl(camera.getStreamUrl())
                .ipAddress(camera.getIpAddress())
                .port(camera.getPort())
                .incidentCount(camera.getIncidentCount())
                .description(camera.getDescription())
                .createdAt(camera.getCreatedAt())
                .updatedAt(camera.getUpdatedAt())
                .build();
    }
}

