package com.smartcity.entities.repository;

import com.smartcity.entities.entity.PublicAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicAssetRepository extends JpaRepository<PublicAsset, String> {
    List<PublicAsset> findByType(String type);
    List<PublicAsset> findByStatus(PublicAsset.Status status);
    List<PublicAsset> findByDepartment(String department);
}

