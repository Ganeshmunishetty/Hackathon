package com.smartcity.usermanagement.controller;

import com.smartcity.usermanagement.dto.RoleDTO;
import com.smartcity.usermanagement.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    
    private final RoleService roleService;
    
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO role = roleService.createRole(roleDTO);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable String id) {
        RoleDTO role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }
    
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable String id, 
                                              @Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO role = roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok(role);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}

