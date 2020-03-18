package com.akp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.akp.exception.NoProductFoundByRegionException;
import com.akp.model.Product;
import com.akp.model.User;
import com.akp.service.ProductService;
import com.akp.service.UserService;

/**
 * @author Aashish Patel
 */
@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/api/rest/product/browse")
    public @ResponseBody ResponseEntity<List<Product>> getEligibleProducts(Principal principal) {

        Optional<User> user = userService.findByUsername(principal.getName());

        logger.info("Filtering the products based on customer region");
        List<Product> products = productService.findAllProductsByRegion(user.get().getCustomer().getRegion());

        if(!products.isEmpty()) {
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        }  else {
            throw new NoProductFoundByRegionException(String.format("No product found matching the customer region: %s") + user.get().getCustomer().getRegion());
        }
    }
}
