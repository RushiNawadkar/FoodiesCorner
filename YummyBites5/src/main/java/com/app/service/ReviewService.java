package com.app.service;

import com.app.dto.ReviewDTO;
import com.app.entities.Review;

public interface ReviewService {

	Review getReviewById(Long id);

	ReviewDTO createReview(ReviewDTO reviewDTO);

	ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);

	void deleteReview(Long id);

}
