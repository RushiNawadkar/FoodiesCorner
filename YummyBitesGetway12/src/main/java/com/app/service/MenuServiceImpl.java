					package com.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.MenuDTO;
import com.app.entities.Menu;
import com.app.entities.Restaurant;
import com.app.repository.MenuRepository;
import com.app.repository.RestaurentRepository;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private RestaurentRepository restaurentRepository;
	
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
    public Menu getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return menu;
    }

    @Override
    public MenuDTO createMenu(MenuDTO menuDTO) {
    	Restaurant restaurant = restaurentRepository.findById(menuDTO.getRestaurantId())
    			.orElseThrow(() -> new ReaserchNotFound("Invalid Restaurent Id!!!"));
    	
        Menu menu = modelMapper.map(menuDTO, Menu.class);
        menu.setRestaurant(restaurant);
        Menu createdMenu = menuRepository.save(menu);
        return menuDTO;
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu not found");
        }
        Menu menu = modelMapper.map(menuDTO, Menu.class);
        menu.setMenuId(id); 
        Menu updatedMenu = menuRepository.save(menu);
        return modelMapper.map(updatedMenu, MenuDTO.class);
    }

    @Override
    public void deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu not found");
        }
        menuRepository.deleteById(id);
    }
    
    @Override
    public List<Restaurant> findRestaurantsByMenuName(String menuName) {
        List<Menu> menus = menuRepository.findByName(menuName);
        Set<Restaurant> restaurants = new HashSet<>();
        for (Menu menu : menus) {
            restaurants.add(menu.getRestaurant());
        }
        return List.copyOf(restaurants);
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> {
                    MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);
                    menuDTO.setRestaurantId(menu.getRestaurant() != null ? menu.getRestaurant().getRestaurantId() : null);
                    return menuDTO;
                })
                .collect(Collectors.toList());
    }
}
