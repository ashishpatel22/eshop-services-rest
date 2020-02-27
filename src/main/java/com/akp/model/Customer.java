package com.akp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aashish Patel
 */
@Entity
@Table(name = "customer")
@Data
public class Customer implements Serializable {

    @Id
    @Column(name = "customer_id")
    private String id;

    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide your frist name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonManagedReference
    private Region region;

    @CreationTimestamp
    private LocalDateTime creationTimestamp; /* Timestamp when the user was created */

    @UpdateTimestamp
    private LocalDateTime updateDateTime; /* Timestamp when the user was last updated */

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Order> orders = new HashSet<>();
}
