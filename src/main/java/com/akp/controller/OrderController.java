package com.akp.controller;

import com.akp.exception.EmptyShoppingCartException;
import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.exception.OrderNotFoundException;
import com.akp.model.Order;
import com.akp.model.PaymentType;
import com.akp.service.OrderService;
import com.akp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Aashish Patel
 */
@RestController
public class OrderController {

    private final UserService userService;

    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/rest/api/order/{orderId}")
    public @ResponseBody
    ResponseEntity<Order> getOrderDetails(@PathVariable("orderId") Long orderId, Principal principal) {

        if (orderService.findById(orderId).isPresent()) {
            return new ResponseEntity<Order>(orderService.findById(orderId).get(), HttpStatus.OK);
        } else {
            throw new OrderNotFoundException(String.format("Invalid order id: %s or user %s is not authorized to see the details for this order", orderId, principal.getName()));
        }
    }

    @GetMapping("/rest/api/order/submit/{paymentType}")
    public @ResponseBody
    ResponseEntity<Order> submitOrder(@PathVariable("paymentType") String paymentType, Principal principal) {

        return new ResponseEntity<Order>(orderService.submitOrder(userService.findByUsername(principal.getName()).get().getCustomer(), PaymentType.fromString(paymentType)), HttpStatus.OK);
    }
}
