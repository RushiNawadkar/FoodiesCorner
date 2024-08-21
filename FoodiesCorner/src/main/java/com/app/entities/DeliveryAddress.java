package com.app.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="deliveryaddress")
public class DeliveryAddress {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long deliveryAddressId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    @JsonIgnore
	    private User user;

	    private String address;
	    private String city;
	    private String state;
	    private String postalCode;
	    private String country;

	    @OneToMany(mappedBy = "deliveryAddress",fetch = FetchType.EAGER)
	    @JsonIgnore
	    private Set<Order> orders;
}
