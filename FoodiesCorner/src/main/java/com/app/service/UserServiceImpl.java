package com.app.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.OrderDTO;
import com.app.dto.UserDTO;
import com.app.entities.User;
import com.app.repository.DeliveryAddressRepository;
import com.app.repository.OrderItemRepository;
import com.app.repository.UserRepository;
import com.app.utils.EmailUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	// dep : dao layer i/f
	@Autowired
	private UserRepository userRepository;
	// dep
	@Autowired
	private ModelMapper modelMapper;
	// dep
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private DeliveryAddressRepository deliveryAddressRepository;

	@Autowired
	private OrderItemRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private EmailUtils emailUtils;

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id " + id));
		modelMapper.map(userDTO, existingUser);
		User updatedUser = userRepository.save(existingUser);
		return modelMapper.map(updatedUser, UserDTO.class);
	}

	@Override
	public void deleteUser(Long id) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id " + id));
		userRepository.delete(existingUser);
	}

//	@Override
//	public Set<OrderDTO> getOrdersByUserId(Long userId) {
//		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//		return user.getOrders().stream().map(order -> modelMapper.map(order, OrderDTO.class))
//				.collect(Collectors.toSet());
//	}

//	@Override
//	public void updatePassword(String email, String oldPassword, String newPassword) {
//		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//
//		if (!encoder.matches(oldPassword, user.getPassword())) {
//			throw new RuntimeException("Old password is incorrect");
//		}
//
//		user.setPassword(encoder.encode(newPassword));
//		userRepository.save(user);
//	}

	@Override
	public UserDTO userRegistration(UserDTO dto) {
		if (userRepository.existsByEmail(dto.getEmail()))
			throw new ReaserchNotFound("Email Already Exists!!!!");
		User user = modelMapper.map(dto, User.class);
		user.setPassword(encoder.encode(user.getPassword()));

		return modelMapper.map(userRepository.save(user), UserDTO.class);
	}

	@Override
	public UserDTO findUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ReaserchNotFound("User not found with email: " + email));
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public Set<OrderDTO> getOrdersByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ReaserchNotFound("User with email " + email + " not found"));

		return user.getOrders().stream().map(order -> {
			OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
			orderDTO.setUserId(order.getUser() != null ? order.getUser().getUserId() : null);
			orderDTO.setDeliveryAddressId(
					order.getDeliveryAddress() != null ? order.getDeliveryAddress().getDeliveryAddressId() : null);
			orderDTO.setDeliveryPersonId(
					order.getDeliveryPerson() != null ? order.getDeliveryPerson().getDeliveryPersonId() : null);
			orderDTO.setRestaurantId(order.getRestaurant() != null ? order.getRestaurant().getRestaurantId() : null);
			return orderDTO;

		}).collect(Collectors.toSet());
	}

	@Override
	public void updatePassword(String email, String oldPassword, String newPassword, Object securityAnswer) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (!encoder.matches(oldPassword, user.getPassword())) {
			throw new RuntimeException("Old password is incorrect");
		}

		if (!securityAnswer.equals(user.getSecurityAnswer())) {
			throw new RuntimeException("Security answer is incorrect");
		}

		user.setPassword(encoder.encode(newPassword));
		userRepository.save(user);

	}

	
	public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        userRepository.save(user);

        emailUtils.sendPasswordResetEmail(email, token);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear token
        user.setResetTokenExpiry(null); // Clear token expiry
        userRepository.save(user);
    }
}
