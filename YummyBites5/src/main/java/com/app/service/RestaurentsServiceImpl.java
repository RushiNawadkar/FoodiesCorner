package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.MenuDTO;
import com.app.dto.RestaurantDTO;
import com.app.entities.Menu;
import com.app.entities.Restaurant;
import com.app.repository.MenuRepository;
import com.app.repository.RestaurentRepository;


@Service
@Transactional
public class RestaurentsServiceImpl implements RestaurentsService {

	@Autowired
	private RestaurentRepository restaurentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MenuRepository menuRepository;
	
	
	@Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
        Restaurant savedRestaurant = restaurentRepository.save(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant existingRestaurant = restaurentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        modelMapper.map(restaurantDTO, existingRestaurant);
        Restaurant updatedRestaurant = restaurentRepository.save(existingRestaurant);
        return modelMapper.map(updatedRestaurant, RestaurantDTO.class);
    }

    @Override
    public void deleteRestaurant(Long id) {
        if (!restaurentRepository.existsById(id)) {
            throw new RuntimeException("Restaurant not found");
        }
        restaurentRepository.deleteById(id);
    }
    
    @Override
    public List<Restaurant> getNearbyHotels(double lat, double lon, double distance) {
        return restaurentRepository.findNearbyHotels(lat, lon, distance);
    }
    
    public List<MenuDTO> getMenusByRestaurentId(Long id) {
        Restaurant restaurent = restaurentRepository.findById(id)
                .orElseThrow(() -> new ReaserchNotFound("Restaurent not found"));

        List<Menu> menus = menuRepository.findByRestaurant(restaurent);
        return menus.stream()
                    .map(menu -> {
                    	MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);
                    	menuDTO.setRestaurantId(menu.getRestaurant()!=null?menu.getRestaurant().getRestaurantId():null);
                    	return menuDTO;
                    })       
                    .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurentRepository.findAll();
        return restaurants.stream()
                          .map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class))
                          .collect(Collectors.toList());
    }
}
