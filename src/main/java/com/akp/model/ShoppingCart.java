package com.akp.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ShoppingCart {

    private Map<Product, Integer> products;
    private BigDecimal total;
    private BigDecimal taxes;
    private Boolean outOfStockStatus;
    private String errorMessage;
}
