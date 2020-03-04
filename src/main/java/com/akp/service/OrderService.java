package com.akp.service;

import com.akp.exception.EmptyShoppingCartException;
import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.model.Customer;
import com.akp.model.Order;
import com.akp.model.PaymentType;
import com.akp.model.ShoppingCart;

import java.util.Optional;

/**
 * @author Aashish Patel
 * Contract for order services
 */

public interface OrderService {

    Optional<Order> findById(Long id);
    Optional<Order> findByIdByCustomer(Long id, Customer customer);
    Order submitOrder(Customer customer, PaymentType paymentType) throws EmptyShoppingCartException, NotEnoughProductsInStockException;
}
