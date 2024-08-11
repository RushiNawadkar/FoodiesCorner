package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Menu;
import com.app.entities.Restaurant;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findByRestaurant(Restaurant restaurant);

	List<Menu> findByName(String name);
}
