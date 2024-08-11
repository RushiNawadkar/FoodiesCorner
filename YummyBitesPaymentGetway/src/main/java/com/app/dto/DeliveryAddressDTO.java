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

public class DeliveryAddressDTO {

	private Long deliveryAddressId;
    private Long user; 
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
