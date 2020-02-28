package com.akp.controller;

import com.akp.exception.InvalidProductIdException;
import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.model.ShoppingCart;
import com.akp.service.ProductService;
import com.akp.service.ShoppingCartService;
import com.akp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Aashish Patel
 */
@RestController
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;

    private final UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/rest/api/shoppingcart")
    public @ResponseBody
    ResponseEntity<ShoppingCart> shoppingCart() {
        return new ResponseEntity<ShoppingCart>(loadShoppingCart(), HttpStatus.OK);
    }

    private ShoppingCart loadShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @GetMapping("/rest/api/shoppingcart/addProduct/{productId}")
    public @ResponseBody
    ResponseEntity<ShoppingCart> addProductToCart(@PathVariable("productId") Long productId, Principal principal) {

        if (productService.findById(productId).isPresent()) {
            if (productService.findById(productId).get().getRegion().getId().equals(userService.findByUsername(principal.getName()).get().getCustomer().getRegion().getId())) {
                productService.findById(productId).ifPresent(shoppingCartService::addProduct);
            } else {
                throw new AccessDeniedException(String.format("Product id %s region : %d does not match with customer region: %k",
                        productId, productService.findById(productId).get().getRegion().getId(), userService.findByUsername(principal.getName()).get().getCustomer().getRegion().getId()));
            }
        } else {
            throw new InvalidProductIdException(String.format("Invalid product id: %s", productId));
        }

        return new ResponseEntity<ShoppingCart>(shoppingCartService.getShoppingCart(), HttpStatus.OK);
    }

    @GetMapping("/rest/api/shoppingcart/removeProduct/{productId}")
    public @ResponseBody
    ResponseEntity<ShoppingCart> removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return new ResponseEntity<ShoppingCart>(shoppingCartService.getShoppingCart(), HttpStatus.OK);
    }

    @GetMapping("/rest/api/shoppingcart/clear")
    public @ResponseBody
    ResponseEntity<ShoppingCart> checkout() {
        shoppingCartService.clearShoppingCart();
        return shoppingCart();
    }
}
