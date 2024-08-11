package com.app.entities;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

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
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Menu> menus;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Order> orders;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Review> reviews;

}
