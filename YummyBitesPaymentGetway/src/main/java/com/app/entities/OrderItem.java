package com.app.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="orderitem")
public class OrderItem {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long orderItemId;

	    @ManyToOne
	    @JoinColumn(name = "order_id", nullable = false)
	    private Order order;

	    @ManyToOne
	    @JoinColumn(name = "menu_id", nullable = false)
	    private Menu menu;

	    private int quantity;
	    private double price;
		
}
