package com.app.controller;

import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.OrderDTO;
import com.app.dto.PasswordUpdateDTO;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.dto.UserDTO;
import com.app.entities.User;
import com.app.repository.UserRepository;
import com.app.security.JwtUtils;
import com.app.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserSignInSignupController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private AuthenticationManager authMgr;

	@Autowired
	private UserRepository repository;

	// sign up
	/*
	 * URL - http://host:port/users/signup Method - POST request payload : sign up
	 * DTO (user details) resp : In case of success : Auth Resp DTO :mesg + JWT
	 * token + SC 201 In case of failure : SC 401
	 * 
	 */
//	@PostMapping("/signup")
//	public ResponseEntity<?> userSignup(@RequestBody @Valid Signup dto) {
//		System.out.println("in sign up " + dto);
//		return ResponseEntity.status(HttpStatus.CREATED).body(userService.userRegistration(dto));
//	}

	/*
	 * URL - http://host:port/users/signin Method - POST request payload : Auth req
	 * DTO : email n password resp : In case of success : Auth Resp DTO :mesg + JWT
	 * token + SC 201 In case of failure : SC 401
	 * 
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody @Valid SigninRequest request) {
		System.out.println("in sign in" + request);
		// 1. create a token(implementation of Authentication i/f)
		// to store un verified user email n pwd
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		// 2. invoke auth mgr's authenticate method;

		User userDTO = repository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ReaserchNotFound("Invalid Email"));
//		System.out.println("=====================>"+userDTO.getName());
		Authentication verifiedToken = authMgr.authenticate(token);
		// => authentication n authorization successful !
		// 3. In case of successful auth, create JWT n send it to the clnt in response
		SigninResponse resp = new SigninResponse(jwtUtils.generateJwtToken(verifiedToken), "Successful Auth!!!!",
				userDTO.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		UserDTO userDTO = userService.getUserById(id);
		return ResponseEntity.ok(userDTO);
	}

//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
//        UserDTO createdUser = userService.createUser(userDTO);
//        return ResponseEntity.ok(createdUser);
//    }

	@PutMapping("/update/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		UserDTO updatedUser = userService.updateUser(id, userDTO);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{userId}/orders")
	public Set<OrderDTO> getOrdersByUserId(@PathVariable Long userId) {
		return userService.getOrdersByUserId(userId);
	}

	@PutMapping("/updatepassword")
	public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		try {
			userService.updatePassword(passwordUpdateDTO.getEmail(), passwordUpdateDTO.getOldPassword(),
					passwordUpdateDTO.getNewPassword());
			return ResponseEntity.ok("Password updated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> userSignup(@RequestBody @Valid UserDTO dto) {
		System.out.println("in sign up " + dto);

		// Register the user
		UserDTO registeredUser = userService.userRegistration(dto);

		// Send confirmation email
		if (registeredUser != null) {
			String email = registeredUser.getEmail();
			sendSignUpConfirmationEmail(email);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
	}

	private void sendSignUpConfirmationEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Successful Sign-Up Notification");
		message.setText("Dear user,\n\nYou have successfully signed for Yummy Bites.\n\nThank you,\nYour Company");

		javaMailSender.send(message);
	}
}
