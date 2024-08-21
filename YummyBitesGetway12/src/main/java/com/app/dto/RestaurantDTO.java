package com.app.dto;

import java.time.LocalDateTime;

import com.app.entities.Role;
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

public class RestaurantDTO {

	private Long restaurantId;
	private String email;
	private String password;
	private Role restrole=Role.ROLE_OWNER;
    private String name;
    private String address;
    private String phone;
    private String openingHours;
    private double latitude;
    private double longitude;
    private double rating;
    private String district;
    private String image_url;
    private String State;
    private String pincode;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
    
}
