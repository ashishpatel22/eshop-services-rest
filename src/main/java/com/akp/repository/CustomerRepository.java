package com.akp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akp.model.Customer;

/**
 * @author Aashish Patel
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
