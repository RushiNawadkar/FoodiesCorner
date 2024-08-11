package com.app.dto;

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

public class OrderItemDTO {

	private Long orderItemId;
    private Long orderId; 
    private Long menuId; 
    private int quantity;
    private double price;
}
