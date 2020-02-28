package com.akp.repository;

import com.akp.model.Customer;
import com.akp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Aashish Patel
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
