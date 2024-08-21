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
import com.app.dto.PasswordResetDTO;
import com.app.dto.PasswordResetRequestDTO;
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
	@GetMapping("/orders/{email}")
	public ResponseEntity<Set<OrderDTO>> getOrdersByUserEmail(@PathVariable String email) {
	    Set<OrderDTO> orders = userService.getOrdersByEmail(email);
	    return ResponseEntity.ok(orders);
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
	    UserDTO userDTO = userService.findUserByEmail(email);
	    return ResponseEntity.ok(userDTO);
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
	
	@PutMapping("/updatepassword")
	public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
	    try {
	        userService.updatePassword(
	                passwordUpdateDTO.getEmail(),
	                passwordUpdateDTO.getOldPassword(),
	                passwordUpdateDTO.getNewPassword(),
	                passwordUpdateDTO.getSecurityAnswer()
	        );
	        return ResponseEntity.ok("Password updated successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
    }
	
	
	@PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequestDTO request) {
        try {
            userService.initiatePasswordReset(request.getEmail());
            return ResponseEntity.ok("Password reset token sent to your email");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	@PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        try {
            userService.resetPassword(resetDTO.getToken(), resetDTO.getNewPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
