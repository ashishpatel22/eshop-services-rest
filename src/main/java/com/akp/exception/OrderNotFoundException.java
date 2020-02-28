package com.akp.exception;

import com.akp.model.Product;

public class OrderNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Not such order found, please check order id";

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderNotFoundException(String s) {
        super(s);
    }
}
