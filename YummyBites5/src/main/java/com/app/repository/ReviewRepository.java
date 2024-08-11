package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
