package com.akp.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.akp.model.Product;
import com.akp.model.Region;

/**
 * @author Aashish Patel
 */
public interface ProductRepository extends JpaRepository<Product, Long>, Serializable {
    Optional<Product> findById(Long id);
    Page<Product> findAllByRegion(Region region, Pageable pageable);
    List<Product> findAllByRegion(Region region);
}
