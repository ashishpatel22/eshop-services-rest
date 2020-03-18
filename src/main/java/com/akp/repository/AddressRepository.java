package com.akp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akp.model.Address;

/**
 * @author Aashish Patel
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

}
