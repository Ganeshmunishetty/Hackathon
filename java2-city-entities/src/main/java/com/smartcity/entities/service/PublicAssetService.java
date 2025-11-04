package com.smartcity.entities.service;

import com.smartcity.entities.dto.PublicAssetDTO;
import com.smartcity.entities.entity.PublicAsset;
import com.smartcity.entities.exception.ResourceNotFoundException;
import com.smartcity.entities.repository.PublicAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicAssetService {
    
    private final PublicAssetRepository assetRepository;
    
    @Transactional
    public PublicAssetDTO createAsset(PublicAssetDTO assetDTO) {
        PublicAsset asset = mapToEntity(assetDTO);
        PublicAsset saved = assetRepository.save(asset);
        return mapToDTO(saved);
    }
    
    public PublicAssetDTO getAssetById(String id) {
        PublicAsset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return mapToDTO(asset);
    }
    
    public List<PublicAssetDTO> getAllAssets() {
        return assetRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PublicAssetDTO> getAssetsByType(String type) {
        return assetRepository.findByType(type).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PublicAssetDTO updateAsset(String id, PublicAssetDTO assetDTO) {
        PublicAsset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        
        updateEntityFromDTO(asset, assetDTO);
        PublicAsset saved = assetRepository.save(asset);
        return mapToDTO(saved);
    }
    
    @Transactional
    public void deleteAsset(String id) {
        if (!assetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asset not found with id: " + id);
        }
        assetRepository.deleteById(id);
    }
    
    private PublicAsset mapToEntity(PublicAssetDTO dto) {
        PublicAsset.PublicAssetBuilder builder = PublicAsset.builder()
                .name(dto.getName())
                .type(dto.getType())
                .location(dto.getLocation())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .description(dto.getDescription())
                .department(dto.getDepartment())
                .lastMaintenance(dto.getLastMaintenance())
                .nextMaintenance(dto.getNextMaintenance());
        
        if (dto.getStatus() != null) {
            builder.status(PublicAsset.Status.valueOf(dto.getStatus()));
        } else {
            builder.status(PublicAsset.Status.OPERATIONAL);
        }
        
        return builder.build();
    }
    
    private void updateEntityFromDTO(PublicAsset asset, PublicAssetDTO dto) {
        if (dto.getName() != null) asset.setName(dto.getName());
        if (dto.getType() != null) asset.setType(dto.getType());
        if (dto.getLocation() != null) asset.setLocation(dto.getLocation());
        if (dto.getLatitude() != null) asset.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) asset.setLongitude(dto.getLongitude());
        if (dto.getStatus() != null) asset.setStatus(PublicAsset.Status.valueOf(dto.getStatus()));
        if (dto.getDescription() != null) asset.setDescription(dto.getDescription());
        if (dto.getDepartment() != null) asset.setDepartment(dto.getDepartment());
        if (dto.getLastMaintenance() != null) asset.setLastMaintenance(dto.getLastMaintenance());
        if (dto.getNextMaintenance() != null) asset.setNextMaintenance(dto.getNextMaintenance());
    }
    
    private PublicAssetDTO mapToDTO(PublicAsset asset) {
        return PublicAssetDTO.builder()
                .id(asset.getId())
                .name(asset.getName())
                .type(asset.getType())
                .location(asset.getLocation())
                .latitude(asset.getLatitude())
                .longitude(asset.getLongitude())
                .status(asset.getStatus().name())
                .description(asset.getDescription())
                .department(asset.getDepartment())
                .lastMaintenance(asset.getLastMaintenance())
                .nextMaintenance(asset.getNextMaintenance())
                .createdAt(asset.getCreatedAt())
                .updatedAt(asset.getUpdatedAt())
                .build();
    }
}

