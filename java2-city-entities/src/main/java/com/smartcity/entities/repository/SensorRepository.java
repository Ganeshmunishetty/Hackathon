package com.smartcity.entities.repository;

import com.smartcity.entities.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    List<Sensor> findByType(String type);
    List<Sensor> findByStatus(Sensor.Status status);
    List<Sensor> findByLocationContaining(String location);
}

