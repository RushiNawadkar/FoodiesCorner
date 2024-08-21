package com.app.service;

import java.util.Set;

import com.app.dto.OrderDTO;
import com.app.dto.Signup;
import com.app.dto.UserDTO;
import com.app.entities.User;

public interface UserService {
	

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUser(Long id);

	UserDTO userRegistration(UserDTO dto);
	
	UserDTO findUserByEmail(String email);

	Set<OrderDTO> getOrdersByEmail(String email);

	void updatePassword(String email, String oldPassword, String newPassword, Object securityAnswer);

	void initiatePasswordReset(String email);

	void resetPassword(String token, String newPassword);

	
    
	
}
