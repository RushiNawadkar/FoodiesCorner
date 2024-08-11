package com.app.dto;

import java.time.LocalDateTime;

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

public class PaymentDTO {

	private Long paymentId;
    private Long orderId; 
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime createdAt;

}
