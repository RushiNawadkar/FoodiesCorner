package com.app.service;

import java.util.Set;

import com.app.dto.OrderDTO;
import com.app.dto.Signup;
import com.app.dto.UserDTO;
import com.app.entities.User;

public interface UserService {
	//add signup method
//	Signup userRegistration(Signup reqDTO);
	
	UserDTO getUserById(Long id);

//	UserDTO createUser(UserDTO userDTO);

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUser(Long id);

	Set<OrderDTO> getOrdersByUserId(Long userId);

	void updatePassword(String email, String oldPassword, String newPassword);

	
	UserDTO userRegistration(UserDTO dto);

	
    
	
}
