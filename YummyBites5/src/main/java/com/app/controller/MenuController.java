package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.app.entities.Menu;
import com.app.entities.Restaurant;
import com.app.service.MenuService;
@CrossOrigin("*")
@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Menu menuDTO = menuService.getMenuById(id);
        return ResponseEntity.ok(menuDTO);
    }

    @PostMapping("add")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        MenuDTO createdMenu = menuService.createMenu(menuDTO);
        return ResponseEntity.ok(createdMenu);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        MenuDTO updatedMenu = menuService.updateMenu(id, menuDTO);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurantsByMenuName(@RequestParam String menuName) {
        return menuService.findRestaurantsByMenuName(menuName);
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        List<MenuDTO> menusDTO = menuService.getAllMenus();
        return ResponseEntity.ok(menusDTO);
    }
}
