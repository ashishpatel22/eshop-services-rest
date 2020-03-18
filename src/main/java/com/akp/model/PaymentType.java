package com.akp.model;

import java.io.Serializable;

public enum PaymentType implements Serializable {

	CREDITCARD("CREDITCARD"), DEBITCARD("DEBITCARD"), COD("COD");

	PaymentType(String type) {
	}
	
    public static PaymentType fromString(String text) {
		    if (text != null) {
		      for (PaymentType b : PaymentType.values()) {
		    	String payemntType = text.toUpperCase(); 
		        if (payemntType.equalsIgnoreCase(b.name())) {
		          return b;
		        }
		      }
		    }
		    return null;
	}
}
