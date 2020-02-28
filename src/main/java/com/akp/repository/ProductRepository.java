package com.akp.repository;

import com.akp.model.Product;
import com.akp.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
public interface ProductRepository extends JpaRepository<Product, Long>, Serializable {
    Optional<Product> findById(Long id);
    Page<Product> findAllByRegion(Region region, Pageable pageable);
    List<Product> findAllByRegion(Region region);
}
