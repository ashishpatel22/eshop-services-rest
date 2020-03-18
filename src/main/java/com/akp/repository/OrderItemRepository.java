package com.akp.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akp.model.OrderItem;

/**
 * @author Aashish Patel
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, Serializable {
    Optional<OrderItem> findById(Long id);
}
