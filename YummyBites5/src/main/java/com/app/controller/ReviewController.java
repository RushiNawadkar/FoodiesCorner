package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ReviewDTO;
import com.app.entities.Review;
import com.app.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	 @GetMapping("/{id}")
	    public Review getReviewById(@PathVariable Long id) {
	        return reviewService.getReviewById(id);
	    }

	    @PostMapping
	    public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO) {
	        return reviewService.createReview(reviewDTO);
	    }

	    @PutMapping("/{id}")
	    public ReviewDTO updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
	        return reviewService.updateReview(id, reviewDTO);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteReview(@PathVariable Long id) {
	        reviewService.deleteReview(id);
	    }
}
