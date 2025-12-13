/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cinebook.model.Role;
import com.cinebook.model.User;
import com.cinebook.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		User user = optionalUser
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
				.password(user.getPasswordHash())
				.authorities(user.getRoles().stream().map(Role::getRoleName).toArray(String[]::new)).build();
	}
}
