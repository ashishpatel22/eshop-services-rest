package com.akp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akp.model.Transaction;

/**
 * @author Aashish Patel
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
