package com.example.novopay.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.novopay.model.Users;
import com.example.novopay.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	/**
	 * The User repository.
	 */
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Users user = userRepository.findByUserId(Long.parseLong(username));
		if (Optional.ofNullable(user).isPresent()) {
			final Set<SimpleGrantedAuthority> roleNames = new HashSet<>();
			return new org.springframework.security.core.userdetails.User(String.valueOf(user.getUserId()),
					user.getPassword(), roleNames);
		} else {
			throw new UsernameNotFoundException("No user present with username: " + username);
		}
	}
}
