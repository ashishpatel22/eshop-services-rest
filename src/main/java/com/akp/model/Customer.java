package com.akp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Aashish Patel
 */
@Entity
@Table(name = "customer")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "creationTimestamp", "updateTimeStamp"})
public class Customer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7642068635078370509L;

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
    @JsonIgnore
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
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();
}
