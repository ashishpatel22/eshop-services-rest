package com.akp.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.model.Product;
import com.akp.model.ShoppingCart;
import com.akp.service.ShoppingCartService;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Aashish Patel
 * Shopping cart serives implementation
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8916822554337085819L;

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private Map<Product, Integer> products = new HashMap<>();

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        logger.info("Inside addPRroduct", ShoppingCartServiceImpl.class);
        if (products.containsKey(product)) {
            logger.debug(String.format("The productID=%d is existing in the cart so will increase the quantity by 1 if product inventory is sufficient ", product.getId()), ShoppingCartServiceImpl.class);
            if (product.getQuantityAvailable() > products.get(product)) {
                logger.info(String.format("The productId=%s has available quantity as=%d so increasing the quantity by one in the cart.", product.getId(), product.getQuantityAvailable()));
                products.replace(product, products.get(product) + 1);
            } else
                throw new NotEnoughProductsInStockException(product);

        } else if (product.getQuantityAvailable() > 0) {
            logger.info("Adding productId=%s to the cart with quantity 1", product.getId());
            products.put(product, 1);
        } else
            throw new NotEnoughProductsInStockException(product);
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    @Override
    public void removeProduct(Product product) {
        logger.info("Inside removeProduct", ShoppingCartServiceImpl.class);
        if (products.containsKey(product)) {
            logger.info(String.format("Cart has productId=", product.getId()), ShoppingCartServiceImpl.class);
            if (products.get(product) > 1) {
                logger.info("Decreasing the product quantity by 1", ShoppingCartServiceImpl.class);
                products.replace(product, products.get(product) - 1);
            } else if (products.get(product) == 1) {
                logger.info(String.format("Removing the productId=%s from the cart since it has just one quantity at present", product.getId()), ShoppingCartServiceImpl.class);
                products.remove(product);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public ShoppingCart getShoppingCart() {
        return new ShoppingCart(getProductsInCart(), getShoppingCartTotal(), new BigDecimal(0.00), null, null);
    }

    /**
     * Clear all the items from the cart
     */
    @Override
    public void clearShoppingCart() {
        products.clear();
    }

    @Override
    public BigDecimal getShoppingCartTotal() {
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
