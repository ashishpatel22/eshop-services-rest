package com.akp.service.impl;

import com.akp.model.Product;
import com.akp.model.Region;
import com.akp.repository.ProductRepository;
import com.akp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Aashish Patel
 * Service implementation for product servies
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> findAllProductsPageable(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findAllProductsByRegion(Region region) {
        return productRepository.findAllByRegion(region);
    }

    @Override
    public Page<Product> findAllProductsByRegionPageable(Region region, Pageable pageable) {
        return productRepository.findAllByRegion(region, pageable);
    }
}
