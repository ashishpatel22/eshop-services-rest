package com.akp.repository;

import com.akp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aashish Patel
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

}
