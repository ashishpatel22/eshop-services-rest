package com.akp.controller;

import com.akp.exception.InvalidProductIdException;
import com.akp.model.Product;
import com.akp.model.ShoppingCart;
import com.akp.model.User;
import com.akp.service.ProductService;
import com.akp.service.ShoppingCartService;
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
public class ShoppingCartController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;

    private final UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/api/rest/shoppingcart")
    public @ResponseBody
    ResponseEntity<ShoppingCart> shoppingCart() {
        logger.info("Inside shoppingCart.", ShoppingCartController.class);
        return new ResponseEntity(loadShoppingCart(), HttpStatus.OK);
    }

    private ShoppingCart loadShoppingCart() {
        logger.info("Inside loadShoppingCart.", ShoppingCartController.class);
        return shoppingCartService.getShoppingCart();
    }

    @GetMapping("/api/rest/shoppingcart/addProduct/{productId}")
    public @ResponseBody
    ResponseEntity<ShoppingCart> addProductToCart(@PathVariable("productId") Long productId, Principal principal) {

        logger.info("Inside addProductToCart.", ShoppingCartController.class);
        String errorMessage = null;
        Optional<User> user = userService.findByUsername(principal.getName());
        Optional<Product> product = productService.findById(productId);
        if (product.isPresent()) {
            logger.info(String.format("Checking if customer=%s region=%s match with the productID=%s region=%s",
                    user.get().getCustomer().getId(), user.get().getCustomer().getRegion().getId(),
                    productId, product.get().getRegion().getId()));
            if (product.get().getRegion().getId().equals(user.get().getCustomer().getRegion().getId())) {
                logger.info(String.format("Adding productId=%s to the cart for the user=%s", productId, user.get().getId()));
                productService.findById(productId).ifPresent(shoppingCartService::addProduct);
            } else {
                errorMessage = String.format("Product id %s region : %s does not match with customer region: %s",
                        productId, product.get().getRegion().getId(), user.get().getCustomer().getRegion().getId());
                logger.debug(errorMessage, ShoppingCartController.class);
                throw new AccessDeniedException(errorMessage);
            }
        } else {
            errorMessage = String.format("Invalid product id: %s", productId);
            logger.debug(errorMessage, ShoppingCartController.class);
            throw new InvalidProductIdException(String.format(errorMessage));
        }

        return new ResponseEntity<ShoppingCart>(shoppingCartService.getShoppingCart(), HttpStatus.OK);
    }

    @GetMapping("/api/rest/shoppingcart/removeProduct/{productId}")
    public @ResponseBody
    ResponseEntity<ShoppingCart> removeProductFromCart(@PathVariable("productId") Long productId, Principal principal) {
        logger.info("Removing productId=%s from shopping cart of user", productId, principal.getName());
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return new ResponseEntity<ShoppingCart>(shoppingCartService.getShoppingCart(), HttpStatus.OK);
    }

    @GetMapping("/api/rest/shoppingcart/clear")
    public @ResponseBody
    ResponseEntity<ShoppingCart> checkout(Principal principal) {
        logger.info("Clearing cart for the user", principal.getName());
        shoppingCartService.clearShoppingCart();
        return shoppingCart();
    }
}