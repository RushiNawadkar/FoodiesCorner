package com.app.dto;

import java.time.LocalDateTime;

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

public class ReviewDTO {

	private Long reviewId;
    private Long userId; 
    private Long restaurantId; 
    private Long deliveryPersonId; 
    private int rating;
    private String comment;
    @JsonIgnore
    private LocalDateTime createdAt;
}
