package com.smartcity.entities.controller;

import com.smartcity.entities.dto.CameraDTO;
import com.smartcity.entities.service.CameraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cameras")
@RequiredArgsConstructor
public class CameraController {
    
    private final CameraService cameraService;
    
    @PostMapping
    public ResponseEntity<CameraDTO> createCamera(@Valid @RequestBody CameraDTO cameraDTO) {
        CameraDTO camera = cameraService.createCamera(cameraDTO);
        return new ResponseEntity<>(camera, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CameraDTO> getCameraById(@PathVariable String id) {
        CameraDTO camera = cameraService.getCameraById(id);
        return ResponseEntity.ok(camera);
    }
    
    @GetMapping
    public ResponseEntity<List<CameraDTO>> getAllCameras(
            @RequestParam(required = false) String status) {
        List<CameraDTO> cameras;
        if (status != null) {
            cameras = cameraService.getCamerasByStatus(status);
        } else {
            cameras = cameraService.getAllCameras();
        }
        return ResponseEntity.ok(cameras);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CameraDTO> updateCamera(@PathVariable String id, 
                                                  @Valid @RequestBody CameraDTO cameraDTO) {
        CameraDTO camera = cameraService.updateCamera(id, cameraDTO);
        return ResponseEntity.ok(camera);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamera(@PathVariable String id) {
        cameraService.deleteCamera(id);
        return ResponseEntity.noContent().build();
    }
}

