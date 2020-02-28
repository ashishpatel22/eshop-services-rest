package com.akp.controller;

import com.akp.exception.NoProductFoundByRegionException;
import com.akp.model.Product;
import com.akp.model.User;
import com.akp.service.ProductService;
import com.akp.service.UserService;
import com.akp.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author Aashish Patel
 */
@RestController
public class ProductController {

    private static final int INITIAL_PAGE = 0;

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

        /* Filtering the products based on customer region */
        List<Product> products = productService.findAllProductsByRegion(user.get().getCustomer().getRegion());

        if(!products.isEmpty()) {

            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        }  else {
            throw new NoProductFoundByRegionException(String.format("No product found matching the customer region: %s") + user.get().getCustomer().getRegion());
        }
    }
}
