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

public class MenuDTO {

	private Long menuId;
    private Long restaurantId; 
    private String imageurl;
    private String name;
    private String description;
    private double price;
     
}
