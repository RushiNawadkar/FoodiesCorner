package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.ReviewDTO;
import com.app.entities.Restaurant;
import com.app.entities.Review;
import com.app.entities.User;
import com.app.repository.RestaurentRepository;
import com.app.repository.ReviewRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurentRepository restaurentRepository;
	
	
	@Override
	public Review getReviewById(Long id) {
		Review review=reviewRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Invalid Id"));
		return review;
	}

	@Override
	public ReviewDTO createReview(ReviewDTO reviewDTO) {
		User user=userRepository.findById(reviewDTO.getUserId()).orElseThrow(()->new ReaserchNotFound("Invalid Id"));
		Restaurant restaurant=restaurentRepository.findById(reviewDTO.getRestaurantId()).orElseThrow(()->new ReaserchNotFound("Invalid Id!!!!!"));
		Review review=modelMapper.map(reviewDTO,Review.class);
		review.setUser(user);
		review.setRestaurant(restaurant);
		review=reviewRepository.save(review);
		return reviewDTO;
	}

	@Override
	public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
		Review existsreview=reviewRepository.findById(id).orElseThrow(()->new ReaserchNotFound("Invalid Id!!!!!"));
		modelMapper.map(reviewDTO, existsreview);
		existsreview=reviewRepository.save(existsreview);
		
		return reviewDTO;
	}

	@Override
	public void deleteReview(Long id) {
		Review review=reviewRepository.findById(id).orElseThrow(()->new ReaserchNotFound("Invalid Id!!!!"));
		reviewRepository.delete(review);
		
	}

	
	
}
