package com.akp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aashish Patel
 */
@Entity
@Table(name = "orders")
@Data
public class Order implements Serializable {

    private static final long serialversionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @CreationTimestamp
    private LocalDateTime orderDateTime; /* Timestamp when the order was created */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @Column(name = "ordertotal", nullable = false)
    @DecimalMin(value = "0.00", message = "*Price has to be non negative number")
    private BigDecimal orderTotal;

    @Column(name = "paymenttype", nullable = false)
    private PaymentType paymentType;

    @Column(name = "orderstatus", nullable = false)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Transaction transaction;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private Set<OrderItem> orderItems = new HashSet<>();
}
