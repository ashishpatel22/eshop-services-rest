package com.akp.service.impl;

import com.akp.exception.EmptyShoppingCartException;
import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.model.*;
import com.akp.repository.OrderItemRepository;
import com.akp.repository.OrderRepository;
import com.akp.repository.ProductRepository;
import com.akp.repository.TransactionRepository;
import com.akp.service.OrderService;
import com.akp.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Aashish Patel
 * Service implementation for order services
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OrderServiceImpl(ShoppingCartService shoppingCartService, OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, TransactionRepository transactionRepository) {
        this.shoppingCartService = shoppingCartService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order submitOrder(Customer customer, PaymentType paymentType) throws EmptyShoppingCartException, NotEnoughProductsInStockException {

        Optional<Product> product;
        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem orderItem = null;
        Order order = null;
        Transaction transaction = null;
        Set<Map.Entry<Product, Integer>> productsEntrySet = shoppingCartService.getProductsInCart().entrySet();
        for (Map.Entry<Product, Integer> entry : productsEntrySet) {
            // Refresh quantity for every product before checking
            product = productRepository.findById(entry.getKey().getId());
            if (product.get().getQuantityAvailable() >= entry.getValue()) {
                orderItem = new OrderItem();
                if(order == null)
                    order = new Order();
                orderItem.setProduct(product.get());
                orderItem.setQuantity(entry.getValue());
                orderItem.setUnitPrice(product.get().getPrice());
                orderItem.setOrderItemtotal(new BigDecimal(entry.getValue() * product.get().getPrice().floatValue()));
                orderItems.add(orderItem);
                orderItem.setOrder(order);
                entry.getKey().setQuantityAvailable(product.get().getQuantityAvailable() - entry.getValue());
                productRepository.saveAll(shoppingCartService.getProductsInCart().keySet());
                productRepository.flush();
            } else {
                throw new NotEnoughProductsInStockException(product.get());
            }
        }
        if(!orderItems.isEmpty()) {
            transaction = new Transaction();
            order.setOrderItems(orderItems);
            order.setCustomer(customer);
            order.setTransaction(transaction);
            transaction.setOrder(order);
            order.setOrderTotal(shoppingCartService.getShoppingCartTotal());
            order.setPaymentType(paymentType);
            order.setOrderStatus(OrderStatus.SUBMITTED);
            orderRepository.save(order);
            orderItemRepository.saveAll(orderItems);
            transactionRepository.save(transaction);
            transactionRepository.flush();
            orderItemRepository.flush();
            orderRepository.flush();
            shoppingCartService.clearShoppingCart();
            return order;
        } else{
            throw new EmptyShoppingCartException("Shopping cart is empty, no item in the shopping cart.");
        }
    }
}
