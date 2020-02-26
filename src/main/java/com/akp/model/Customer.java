package com.akp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author Aashish Patel
 */
@Entity
@Table(name = "customer")
@Data
public class Customer {

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
    private Region region;

    @CreationTimestamp
    private LocalDateTime creationTimestamp; /* Timestamp when the user was created */

    @UpdateTimestamp
    private LocalDateTime updateDateTime; /* Timestamp when the user was last updated */

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public String toString() {
        return "" + id;
    }
}
