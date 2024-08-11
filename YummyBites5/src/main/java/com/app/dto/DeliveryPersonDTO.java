package com.app.dto;

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

public class DeliveryPersonDTO {

	 private Long deliveryPersonId;
	    private String name;
	    private String phone;
	    private String vehicleInfo;
	    private String status;
	    @JsonIgnore
	    private Set<OrderDTO> orders;

}
