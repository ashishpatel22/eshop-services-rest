package com.akp.repository;

import com.akp.model.Order;
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
public interface OrderRepository extends JpaRepository<Order, Long>, Serializable {
    Optional<Order> findById(Long id);
}
