package com.akp.repository;

import com.akp.model.Order;
import com.akp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, Serializable {
    Optional<OrderItem> findById(Long id);
}
