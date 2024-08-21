package com.app.dto;

import java.time.LocalDateTime;
import java.util.Set;

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

public class OrderDTO {

	private Long orderId;
    private Long userId; 
    private Long restaurantId; 
    private Long deliveryAddressId; 
    private String orderStatus;
    private double totalAmount;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
    private PaymentDTO payment;
    private Long deliveryPersonId; 
}
