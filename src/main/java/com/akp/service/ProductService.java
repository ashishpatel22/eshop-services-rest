package com.akp.service;

import com.akp.model.Product;
import com.akp.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Aashish Patel
 * Contract for product services
 */

public interface ProductService {

    Optional<Product> findById(Long id);

    Page<Product> findAllProductsPageable(Pageable pageable);

    Page<Product> findAllProductsByRegionPageable(Region region, Pageable pageable);

    List<Product> findAllProductsByRegion(Region region);
}
