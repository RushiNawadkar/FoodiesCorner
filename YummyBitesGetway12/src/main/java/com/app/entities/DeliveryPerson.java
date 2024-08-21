package com.app.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="deliveryperson")
public class DeliveryPerson {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long deliveryPersonId;

	    private String name;
	    private String phone;
	    private String vehicleInfo;
	    private String status;

	    @OneToMany(mappedBy = "deliveryPerson")
	    @JsonIgnore
	    private Set<Order> orders;
}
