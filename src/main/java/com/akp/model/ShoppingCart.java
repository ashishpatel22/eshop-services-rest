package com.akp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class ShoppingCart {

    private Map<Product, Integer> products = new HashMap<>();
    private BigDecimal total;
    private Boolean outOfStockStatus;
    private String errorMessage;
}
