package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.MenuDTO;
import com.app.dto.RestaurantDTO;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.entities.Restaurant;
import com.app.repository.RestaurentRepository;
import com.app.security.JwtUtils;
import com.app.service.RestaurentsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest")
public class RestaurantController {

	@Autowired
	private RestaurentsService restaurentsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private AuthenticationManager authMgr;
	
	@Autowired
	private RestaurentRepository repository;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody @Valid SigninRequest request) {
		System.out.println("in sign in" + request);
		// 1. create a token(implementation of Authentication i/f)
		// to store un verified user email n pwd
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		// 2. invoke auth mgr's authenticate method;

		Restaurant restaurantDTO = repository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ReaserchNotFound("Invalid Email"));
//		System.out.println("=====================>"+userDTO.getName());
		Authentication verifiedToken = authMgr.authenticate(token);
		// => authentication n authorization successful !
		// 3. In case of successful auth, create JWT n send it to the clnt in response
		SigninResponse resp = new SigninResponse(jwtUtils.generateJwtToken(verifiedToken), "Successful Auth!!!!",
				restaurantDTO.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        RestaurantDTO restaurantDTO = restaurentsService.getRestaurantById(id);
        return ResponseEntity.ok(restaurantDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
    	System.out.println("in sign up " + restaurantDTO);
    	RestaurantDTO createdRestaurant = restaurentsService.createRestaurant(restaurantDTO);
    	if(createdRestaurant!=null) {
    		String email=createdRestaurant.getEmail();
    		sendSignUpConfirmationEmail(email);
    	}
        return ResponseEntity.ok(createdRestaurant);
    }

    private void sendSignUpConfirmationEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Successful Sign-Up Notification");
		message.setText("Dear Resataurents Owner,\n\nYou have successfully signed for Yummy Bites.\n\nThank you,\nYour Company");

		javaMailSender.send(message);
	}
    @PutMapping("/update/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO updatedRestaurant = restaurentsService.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurentsService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/nearby")
	public List<Restaurant> getNearbyHotels(@RequestParam double lat, @RequestParam double lon,
			@RequestParam(defaultValue = "10") double distance) { // Default distance is 10 km
		return restaurentsService.getNearbyHotels(lat, lon, distance);
	}
    
    @GetMapping("/{id}/menus")
    public ResponseEntity<List<MenuDTO>> getMenusByRestaurentId(@PathVariable Long id) {
        List<MenuDTO> menus = restaurentsService.getMenusByRestaurentId(id);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurentsService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
    
    
}
