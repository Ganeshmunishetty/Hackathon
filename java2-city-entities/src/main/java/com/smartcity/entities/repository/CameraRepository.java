package com.smartcity.entities.repository;

import com.smartcity.entities.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, String> {
    List<Camera> findByStatus(Camera.Status status);
    List<Camera> findByLocationContaining(String location);
}

