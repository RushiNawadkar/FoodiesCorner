package com.app.service;

import java.util.List;

import com.app.dto.MenuDTO;
import com.app.entities.Menu;
import com.app.entities.Restaurant;

public interface MenuService {

	Menu getMenuById(Long id);

	MenuDTO createMenu(MenuDTO menuDTO);

	MenuDTO updateMenu(Long id, MenuDTO menuDTO);

	void deleteMenu(Long id);
	
	List<Restaurant> findRestaurantsByMenuName(String menuName);

	List<MenuDTO> getAllMenus();
}
