package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // to enable spring sec frmwork support
@Configuration // to tell SC , this is config class containing @Bean methods
@EnableGlobalMethodSecurity(prePostEnabled = true)
//To enable method level authorization support : pre n post authorization
public class SecurityConfig {
	// dep : pwd encoder
	@Autowired
	private PasswordEncoder enc;
	// dep : custom jwt auth filter
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	// dep : custom auth entry point
	@Autowired
	private CustomAuthenticationEntryPoint authEntry;

	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception {
		// URL based authorization rules
		http.cors().and()
				// disable CSRF token generation n verification
				.csrf().disable()
				// configure exception handling for auth entry point - currently not required !
//		.exceptionHandling().authenticationEntryPoint(authEntry)
//		.and()
				.authorizeRequests()
				.antMatchers("/users/signup", "/users/signin", "/v*/api-doc*/**", "/swagger-ui/**")
				.permitAll().antMatchers("/users/{id}").hasAnyRole("ADMIN", "USER").antMatchers("/users/updatepassword")
				.permitAll().antMatchers("/add").hasAnyRole("ADMIN", "EMPLOYEE").antMatchers("/rest/all").permitAll()
				.antMatchers("/rest/{id}/menus","/rest/signin").permitAll()
				.antMatchers("/rest/{id}","rest/update/{id}", "rest/delete/{id}")
				.permitAll()// Allow all /rest endpoints without authentication
				.antMatchers("/users/add", "/users/update/{id}", "/users/delete/{id}").hasAnyRole("ADMIN", "USER")
				.antMatchers("/users/{userId}/orders").hasAnyRole("USER", "ADMIN") 
				.antMatchers("/orderitem/{id}", "/orderitem/add", "/order/update/{id}", "/orderitem/delete/{id}")
				.permitAll().antMatchers("/order/{id}", "/order/add", "/order/delete/{id}", "/order/update/{id}")
				.permitAll().antMatchers("/menu/add").hasAnyRole("ADMIN", "OWNER")
				.antMatchers("/menu/{id}", "/menu/update/{id}", "/menu/delete/{id}",
						"/menu/restaurants").hasAnyRole("ADMIN","OWNER")
				
				.antMatchers("/menu/getAll").permitAll().antMatchers("/deliveryPerson/{id}")
				.hasAnyRole("ADMIN", "EMPLOYEE").antMatchers("/deliveryPerson/add", "/deliveryPerson/update/{id}")
				.hasRole("ADMIN")

				// only required for JS clnts (react / angular) : for the pre flight requests
				.antMatchers(HttpMethod.OPTIONS).permitAll()

				.anyRequest().authenticated().and()
				// to tell spring sec : not to use HttpSession to store user's auth details
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// inserting jwt filter before sec filter
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// configure AuthMgr as a spring bean
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
