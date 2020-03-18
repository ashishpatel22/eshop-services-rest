package com.akp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.akp.model.Region;
import com.akp.model.Role;

/**
 * @author Aashish Patel
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
    Role findByDescription(@Param("role") String region);
}
