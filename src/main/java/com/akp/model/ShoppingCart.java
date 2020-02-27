package com.akp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class ShoppingCart {

    private Map<Product, Integer> products;
    private BigDecimal total;
    private BigDecimal taxes;
    private Boolean outOfStockStatus;
    private String errorMessage;
}
