package com.akp.exception;

/**
 * @author Aashish Patel
 */
public class OrderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6346499142011074069L;
	private static final String DEFAULT_MESSAGE = "Not such order found, please check order id";

	public OrderNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public OrderNotFoundException(String s) {
		super(s);
	}
}
