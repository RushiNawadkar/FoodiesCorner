package com.app.service;

import java.util.List;

import com.app.dto.MenuDTO;
import com.app.dto.RestaurantDTO;
import com.app.entities.Restaurant;

public interface RestaurentsService {

	RestaurantDTO getRestaurantById(Long id);

	RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);

	RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO);

	void deleteRestaurant(Long id);

	List<Restaurant> getNearbyHotels(double lat, double lon, double distance);

	List<MenuDTO> getMenusByRestaurentId(Long id);

	List<RestaurantDTO> getAllRestaurants();

	

	

}
