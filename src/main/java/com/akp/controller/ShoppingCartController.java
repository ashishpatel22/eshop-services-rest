package com.akp.controller;

import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.model.ShoppingCart;
import com.akp.model.User;
import com.akp.service.ProductService;
import com.akp.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Aashish Patel
 */
@RestController
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping("/rest/api/shoppingcart")
    public @ResponseBody
    ResponseEntity<ShoppingCart>  shoppingCart() {
        return new ResponseEntity<ShoppingCart>(loadShoppingCart(), HttpStatus.OK);
    }

    private ShoppingCart loadShoppingCart() {
        return shoppingCartService.getCart();
    }

    @GetMapping("/rest/api/shoppingcart/addProduct/{productId}")
    public @ResponseBody
    ResponseEntity<ShoppingCart>  addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
        return new ResponseEntity<ShoppingCart>(shoppingCartService.getCart(), HttpStatus.OK);
    }

    @GetMapping("/rest/api/shoppingcart/removeProduct/{productId}")
    public @ResponseBody
    ResponseEntity<ShoppingCart>  removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return new ResponseEntity<ShoppingCart>(shoppingCartService.getCart(), HttpStatus.OK);
    }

    @GetMapping("/rest/api/shoppingcart/checkout")
    public @ResponseBody
    ResponseEntity<ShoppingCart>  checkout() {
        try {
            shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            ShoppingCart shoppingCart = loadShoppingCart();
            shoppingCart.setOutOfStockStatus(true);
            shoppingCart.setErrorMessage(e.getMessage());
            return new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.OK);
        }
        return shoppingCart();
    }
}
