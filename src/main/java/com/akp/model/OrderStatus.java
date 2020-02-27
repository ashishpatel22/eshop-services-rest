package com.akp.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {

    SUBMITTED("submitted"),
    PROCESSED("processed"),
    DELIVERED("delivered"),
    REFUNDED("refunded"),
    CANCELED("canceled"),
    ;

    private String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
