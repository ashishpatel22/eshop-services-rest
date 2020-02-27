package com.akp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Aashish Patel
 */
@Entity
@Table(name = "orderItem")
@Data
@NoArgsConstructor
public class OrderItem implements Serializable {

    private static final long serialversionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderitem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "*Quantity has to be non negative number")
    private Integer quantity;

    @Column(name = "unitPrice", nullable = false)
    @DecimalMin(value = "0.00", message = "*Price has to be non negative number")
    private BigDecimal unitPrice;

    @Column(name = "orderItemtotal", nullable = false)
    private BigDecimal orderItemtotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Order order;
}
