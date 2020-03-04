package com.akp.controller;

import com.akp.exception.OrderNotFoundException;
import com.akp.model.Customer;
import com.akp.model.Order;
import com.akp.model.PaymentType;
import com.akp.service.OrderService;
import com.akp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

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

        logger.info(String.format("Trying to fetch details for the orderID=%s by the user=%s", orderId, principal.getName()));
        Customer customer = userService.findByUsername(principal.getName()).get().getCustomer();
        return new ResponseEntity<Order>(orderService.findByIdByCustomer(orderId, customer).get(), HttpStatus.OK);
    }

    @GetMapping("/rest/api/order/submit/{paymentType}")
    public @ResponseBody
    ResponseEntity<Order> submitOrder(@PathVariable("paymentType") String paymentType, Principal principal) {
        logger.info("Submitting order for the user=%s, usign payment type=%s", principal.getName(), paymentType);
        return new ResponseEntity<Order>(orderService.submitOrder(userService.findByUsername(principal.getName()).get().getCustomer(), PaymentType.fromString(paymentType)), HttpStatus.OK);
    }
}
