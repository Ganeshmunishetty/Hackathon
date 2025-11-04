package com.smartcity.usermanagement.service;

import com.smartcity.usermanagement.dto.RoleDTO;
import com.smartcity.usermanagement.entity.Permission;
import com.smartcity.usermanagement.entity.Role;
import com.smartcity.usermanagement.exception.BadRequestException;
import com.smartcity.usermanagement.exception.ResourceNotFoundException;
import com.smartcity.usermanagement.repository.PermissionRepository;
import com.smartcity.usermanagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new BadRequestException("Role already exists");
        }
        
        Role role = Role.builder()
                .name(roleDTO.getName())
                .description(roleDTO.getDescription())
                .build();
        
        if (roleDTO.getPermissions() != null && !roleDTO.getPermissions().isEmpty()) {
            Set<Permission> permissions = roleDTO.getPermissions().stream()
                    .map(permissionName -> permissionRepository.findByName(permissionName)
                            .orElseThrow(() -> new ResourceNotFoundException("Permission not found: " + permissionName)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }
        
        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }
    
    public RoleDTO getRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return mapToDTO(role);
    }
    
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public RoleDTO updateRole(String id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        
        if (!role.getName().equals(roleDTO.getName()) && roleRepository.existsByName(roleDTO.getName())) {
            throw new BadRequestException("Role name already exists");
        }
        
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        
        if (roleDTO.getPermissions() != null) {
            Set<Permission> permissions = roleDTO.getPermissions().stream()
                    .map(permissionName -> permissionRepository.findByName(permissionName)
                            .orElseThrow(() -> new ResourceNotFoundException("Permission not found: " + permissionName)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }
        
        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }
    
    @Transactional
    public void deleteRole(String id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
    
    private RoleDTO mapToDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions().stream()
                        .map(Permission::getName)
                        .collect(Collectors.toSet()))
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}

