package com.authorization.services;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.authorization.model.Role;
import com.authorization.model.User;
import com.authorization.repository.UserRepository;

@SpringBootTest
class UserDetailsServiceTest {

	@Autowired
	UserDetailsService userService;

	@MockBean
	UserRepository userRepository;

	Optional<User> getUser() {
		User user = new User();
		Role role = new Role();
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		role.setName("test");
		user.setUsername("testUser");
		user.setPassword("testpassword");
		user.setRoles(roles);
		Optional<User> users = Optional.of(user);
		return users;
	}

	Optional<User> getEmptyUser() {
		Optional<User> users = Optional.empty();
		return users;
	}

	@Test
	void testLoadUserByNonExistentUsername() {
		Optional<User> user = getUser();
		User userToLoad = user.get();
		String username = userToLoad.getUsername();
		when(userRepository.findById(username)).thenReturn(getEmptyUser());
		assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> {

			userService.loadUserByUsername(username);
		});

	}

	@Test
	void testLoadUserByUsername() {
		Optional<User> user = getUser();
		User userToLoad = user.get();
		when(userRepository.findById(userToLoad.getUsername())).thenReturn(getUser());
		UserDetails loadUserByUsername = userService.loadUserByUsername(userToLoad.getUsername());
		assertEquals(userToLoad.getUsername(), loadUserByUsername.getUsername());
		assertEquals(userToLoad.getPassword(), loadUserByUsername.getPassword());

	}
}
