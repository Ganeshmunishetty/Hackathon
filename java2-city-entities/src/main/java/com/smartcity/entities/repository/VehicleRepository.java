package com.smartcity.entities.repository;

import com.smartcity.entities.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    List<Vehicle> findByType(String type);
    List<Vehicle> findByStatus(Vehicle.Status status);
    List<Vehicle> findByDepartment(String department);
}

