package com.akp.service.impl;

import com.akp.controller.OrderController;
import com.akp.exception.EmptyShoppingCartException;
import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.exception.OrderNotFoundException;
import com.akp.model.*;
import com.akp.repository.OrderItemRepository;
import com.akp.repository.OrderRepository;
import com.akp.repository.ProductRepository;
import com.akp.repository.TransactionRepository;
import com.akp.service.OrderService;
import com.akp.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

/**
 * @author Aashish Patel
 * Service implementation for order services
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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
        logger.info(String.format("Fetching order details for order id=%s", id));
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> findByIdByCustomer(Long orderId, Customer customer) {

        Optional<Order> order = findById(orderId);

        if (!order.isPresent()) {
            throw new OrderNotFoundException(String.format("Invalid order id: %s", orderId));
        }
        if (order.get().getCustomer().equals(customer)) {
            logger.info(String.format("Order id %s belongs to the the user in session=%s", orderId, customer.getUser().getUsername()));
            logger.debug(String.format("Order id=%s details are=%s", order, order.toString()));
            return order;
        }
        for(Role role : customer.getUser().getRoles()) {
            logger.info(String.format("Checking if the user=%s has ADMIN rights to check orders from other users.", customer.getUser().getUsername()));
            if (role.getRole().equals("ROLE_ADMIN")) {
                logger.info(String.format("User=%s has admin rights to see the order details for other customers", customer.getUser().getUsername()));
                logger.info(String.format("Order id=%s details are=%s", order.get().getId(), order.toString()));
                return order;
            }
        }
        throw new AccessDeniedException(String.format("Order=%s belongs to some other user or user=%s is not authorized to see the details", orderId, customer.getUser().getUsername()));
    }

    @Override
    @Transactional
    public Order submitOrder(Customer customer, PaymentType paymentType) throws EmptyShoppingCartException, NotEnoughProductsInStockException {

        logger.info("Inside submitOrder", OrderServiceImpl.class);
        logger.info(String.format("Performing cart submission fro the customer=%s", customer.getId()));

        Optional<Product> product;
        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem orderItem = null;
        Order order = null;
        Transaction transaction = null;
        Set<Map.Entry<Product, Integer>> productsEntrySet = shoppingCartService.getProductsInCart().entrySet();
        logger.info("Iterating over each cart item row to check if inventory is available and if so block the inventory");
        for (Map.Entry<Product, Integer> entry : productsEntrySet) {
            // Refresh quantity for every product before checking
            logger.info("Refreshing quantity for every product before checking");
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
