package com.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateDTO {

	private String email;
    private String oldPassword;
    private String newPassword;
	public Object getSecurityAnswer() {
		// TODO Auto-generated method stub
		return null;
	}
}
