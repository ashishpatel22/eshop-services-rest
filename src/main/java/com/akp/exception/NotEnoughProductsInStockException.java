package com.akp.exception;

import com.akp.model.Product;

/**
 * @author Aashish Patel
 */
public class NotEnoughProductsInStockException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -262880017804655649L;
	private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public NotEnoughProductsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductsInStockException(Product product) {
        super(String.format("Not enough %s products in stock. Only %d left", product.getName(), product.getQuantityAvailable()));
    }

}
