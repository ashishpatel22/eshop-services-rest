package com.akp.service.impl;

import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.model.Product;
import com.akp.model.ShoppingCart;
import com.akp.service.ShoppingCartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Aashish Patel
 * Shopping cart serives implementation
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService, Serializable {

    private static final long serialversionUID = 129348938L;

    private Map<Product, Integer> products = new HashMap<>();

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            if(product.getQuantityAvailable() > products.get(product)) {
                products.replace(product, products.get(product) + 1);
            } else
                throw new NotEnoughProductsInStockException(product);

        } else if (product.getQuantityAvailable() > 0) {
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
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
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
     *
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
