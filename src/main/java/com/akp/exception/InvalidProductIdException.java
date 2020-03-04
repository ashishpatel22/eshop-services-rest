package com.akp.exception;

/**
 * @author Aashish Patel
 */
public class InvalidProductIdException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Product id is not valid";

    public InvalidProductIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidProductIdException(String s) {
        super(s);
    }
}
