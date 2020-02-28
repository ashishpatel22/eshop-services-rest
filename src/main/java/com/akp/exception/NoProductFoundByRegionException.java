package com.akp.exception;

public class NoProductFoundByRegionException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No product found matching the customer region";

    public NoProductFoundByRegionException() {
        super(DEFAULT_MESSAGE);
    }

    public NoProductFoundByRegionException(String s) {
        super(s);
    }
}
