package com.akp.repository;

import com.akp.model.Region;
import com.akp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Aashish Patel
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
    Role findByDescription(@Param("role") String region);
}
