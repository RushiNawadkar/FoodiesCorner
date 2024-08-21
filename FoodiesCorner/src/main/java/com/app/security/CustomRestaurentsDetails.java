package com.app.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.entities.Restaurant;

public class CustomRestaurentsDetails implements UserDetails {
	private Restaurant rest;

	public CustomRestaurentsDetails(Restaurant rest) {
		super();
		this.rest = rest;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ret list of granted authorities
		// GrantedAuthority : i/f -<--- SimpleGrantedAuthority(String role)
		return List.of(new SimpleGrantedAuthority
				(rest.getRestrole().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return rest.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return rest.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Restaurant getRestaurant() {
		return rest;
	}
	

}
