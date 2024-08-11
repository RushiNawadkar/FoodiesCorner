package com.app.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ApiException;
import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.OrderDTO;
import com.app.dto.Signup;
import com.app.dto.UserDTO;
import com.app.entities.User;
import com.app.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {
	//dep : dao layer i/f
		@Autowired
		private UserRepository userRepository;
		//dep
		@Autowired
		private ModelMapper modelMapper;
		//dep 
		@Autowired
		private PasswordEncoder encoder;
		
		
		
//	@Override
//	public Signup userRegistration(Signup reqDTO) {
//		//dto --> entity
//		User user=modelMapper.map(reqDTO, User.class);
//		if(userRepository.existsByEmail(reqDTO.getEmail()))
//			throw new ApiException("Email already exists !!!");
//		
//		user.setPassword(encoder.encode(user.getPassword()));//pwd : encrypted using SHA
//		return modelMapper.map(userRepository.save(user), Signup.class);
//	}

	@Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return modelMapper.map(user, UserDTO.class);
    }

//    @Override
//    public UserDTO createUser(UserDTO userDTO) {
//        User user = modelMapper.map(userDTO, User.class);
//        User savedUser = userRepository.save(user);
//        return modelMapper.map(savedUser, UserDTO.class);
//    }

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
    
    @Override
    public Set<OrderDTO> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getOrders().stream()
            .map(order -> modelMapper.map(order, OrderDTO.class))
            .collect(Collectors.toSet());
    }

	@Override
	public void updatePassword(String email, String oldPassword, String newPassword) {
		User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

	@Override
	public UserDTO userRegistration(UserDTO dto) {
	      if(userRepository.existsByEmail(dto.getEmail()))
	    	  throw new ReaserchNotFound("Email Already Exists!!!!");
	      User user=modelMapper.map(dto, User.class);
	      user.setPassword(encoder.encode(user.getPassword()));
	    	  
		return modelMapper.map(userRepository.save(user),UserDTO.class);
	}
		
	
	

	

	
}
