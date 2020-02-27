package com.akp.controller;

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
    ResponseEntity<Order> getOrderDetails(@PathVariable("orderId") Long orderId, Principal principal) throws Exception {

        if (orderService.findById(orderId).isPresent() && userService.findByUsername(principal.getName()).isPresent()) {
            return new ResponseEntity<Order>(orderService.findById(orderId).get(), HttpStatus.OK);
        } else {
            throw new Exception("Invalid order id or customer is not authenticated");
        }
    }

    @GetMapping("/rest/api/order/submit/{paymentType}")
    public @ResponseBody
    ResponseEntity<Order> submitOrder(@PathVariable("paymentType") String paymentType, Principal principal) throws Exception {
        if (userService.findByUsername(principal.getName()).isPresent()) {
            return new ResponseEntity<Order>(orderService.submitOrder(userService.findByUsername(principal.getName()).get().getCustomer(), PaymentType.fromString(paymentType)), HttpStatus.OK);
        } else {
            throw new Exception("User is not authenticated");
        }
    }
}
