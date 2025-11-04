package com.smartcity.entities.controller;

import com.smartcity.entities.dto.PublicAssetDTO;
import com.smartcity.entities.service.PublicAssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class PublicAssetController {
    
    private final PublicAssetService assetService;
    
    @PostMapping
    public ResponseEntity<PublicAssetDTO> createAsset(@Valid @RequestBody PublicAssetDTO assetDTO) {
        PublicAssetDTO asset = assetService.createAsset(assetDTO);
        return new ResponseEntity<>(asset, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PublicAssetDTO> getAssetById(@PathVariable String id) {
        PublicAssetDTO asset = assetService.getAssetById(id);
        return ResponseEntity.ok(asset);
    }
    
    @GetMapping
    public ResponseEntity<List<PublicAssetDTO>> getAllAssets(
            @RequestParam(required = false) String type) {
        List<PublicAssetDTO> assets;
        if (type != null) {
            assets = assetService.getAssetsByType(type);
        } else {
            assets = assetService.getAllAssets();
        }
        return ResponseEntity.ok(assets);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PublicAssetDTO> updateAsset(@PathVariable String id, 
                                                       @Valid @RequestBody PublicAssetDTO assetDTO) {
        PublicAssetDTO asset = assetService.updateAsset(id, assetDTO);
        return ResponseEntity.ok(asset);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable String id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}

