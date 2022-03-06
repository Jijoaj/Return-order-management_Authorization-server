package com.authorization.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authorization.model.User;
import com.authorization.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(username);
		if (user.isPresent()) {
			User extractedUser = user.get();
			return new org.springframework.security.core.userdetails.User(extractedUser.getUsername(),
					extractedUser.getPassword(), extractedUser.getRoles());
		} else {
			throw new UsernameNotFoundException("no user found with username" + username);
		}
	}

}
