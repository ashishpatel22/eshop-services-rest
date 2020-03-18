package com.akp.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akp.model.Order;

/**
 * @author Aashish Patel
 */
public interface OrderRepository extends JpaRepository<Order, Long>, Serializable {
    Optional<Order> findById(Long id);
}
