package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.MenuDTO;
import com.app.dto.RestaurantDTO;
import com.app.entities.Restaurant;
import com.app.service.RestaurentsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest")
public class RestaurantController {

	@Autowired
	private RestaurentsService restaurentsService;
	
	@GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        RestaurantDTO restaurantDTO = restaurentsService.getRestaurantById(id);
        return ResponseEntity.ok(restaurantDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO createdRestaurant = restaurentsService.createRestaurant(restaurantDTO);
        return ResponseEntity.ok(createdRestaurant);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO updatedRestaurant = restaurentsService.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurentsService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/nearby")
	public List<Restaurant> getNearbyHotels(@RequestParam double lat, @RequestParam double lon,
			@RequestParam(defaultValue = "10") double distance) { // Default distance is 10 km
		return restaurentsService.getNearbyHotels(lat, lon, distance);
	}
    
    @GetMapping("/{id}/menus")
    public ResponseEntity<List<MenuDTO>> getMenusByRestaurentId(@PathVariable Long id) {
        List<MenuDTO> menus = restaurentsService.getMenusByRestaurentId(id);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurentsService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
}
