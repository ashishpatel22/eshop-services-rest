package com.akp.repository;

import com.akp.model.Role;
import com.akp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Aashish Patel
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
