package com.akp.exception;

/**
 * @author Aashish Patel
 */
public class EmptyShoppingCartException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Shopping cart is empty, no item in the shopping cart.";

    public EmptyShoppingCartException() {
        super(DEFAULT_MESSAGE);
    }

    public EmptyShoppingCartException(String s) {
        super(s);
    }
}
