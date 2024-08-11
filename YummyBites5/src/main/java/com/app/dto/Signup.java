package com.app.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.app.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Signup {
	@JsonProperty(access = Access.READ_ONLY) // this property only used during ser.
	private Long id;
	@NotBlank(message = "First Name required")
	private String Name;
	@Email(message = "Invalid Email!!!")
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)// this property only used during de-ser.
	private String password;
	private Role role;
	private String phone;
	
	
	@JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
	public Signup(Long id, @NotBlank(message = "First Name required") String name,
			@Email(message = "Invalid Email!!!") String email, String password, Role role, String phone,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		Name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phone = phone;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
	
}
