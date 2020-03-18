package com.akp.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import com.akp.model.Product;
import com.akp.model.ShoppingCart;

/**
 * @author Aashish Patel
 * Contract for shopping cart services
 */
public interface ShoppingCartService extends Serializable {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void clearShoppingCart();

    BigDecimal getShoppingCartTotal();

    ShoppingCart getShoppingCart();
}
