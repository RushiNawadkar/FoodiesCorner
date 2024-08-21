package com.app.entities;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;

	    private String name;
	    
	    private String email;
	    
	    private String phone;
	    
	    @Enumerated(EnumType.STRING)
	    private Role role=Role.ROLE_USER;
	    
	    private String password;
	    
	    @CreationTimestamp
	    @JsonIgnore
	    private LocalDateTime  createdAt;
	    @UpdateTimestamp
	    @JsonIgnore
	    private LocalDateTime  updatedAt;

	    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	    @JsonIgnore
	    private Set<Order> orders;

	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	    @JsonIgnore
	    private Set<DeliveryAddress> deliveryAddresses;

	    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	    @JsonIgnore
	    private Set<Review> reviews;
	    
	    private String securityQuestion;
	    
	    private String securityAnswer;
	    
	    private String resetToken; 
	    private LocalDateTime resetTokenExpiry;

	    
	    public User(String name, String email, String phone, Role role, String password, String securityQuestion, String securityAnswer) {
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.role = role;
	        this.password = password;
	        this.securityQuestion = securityQuestion;
	        this.securityAnswer = securityAnswer;
	    }


		

}
