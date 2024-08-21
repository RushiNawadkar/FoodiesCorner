package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Restaurant;
import com.app.entities.User;

public interface RestaurentRepository extends JpaRepository<Restaurant, Long> {

	@Query(value = "SELECT *, (6371 * ACOS(COS(RADIANS(:lat)) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(:lon)) + SIN(RADIANS(:lat)) * SIN(RADIANS(latitude)))) AS distance " +
            "FROM Restaurant " +
            "HAVING distance <= :distance " +
            "ORDER BY distance", nativeQuery = true)
     List<Restaurant> findNearbyHotels(@Param("lat") double lat, @Param("lon") double lon, @Param("distance") double distance);

	Optional<Restaurant> findByEmail(String email);

	boolean existsByEmail(String email);
	
	 
}
