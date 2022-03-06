package com.authorization.services;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.authorization.exceptions.RoleNotFoundException;
import com.authorization.exceptions.UnableToAddNewUserException;
import com.authorization.exceptions.UsernameExistsException;
import com.authorization.model.Role;
import com.authorization.model.User;
import com.authorization.repository.RoleRepository;
import com.authorization.repository.UserRepository;

@SpringBootTest
class TestUserServiceNegativeCases {

	@Autowired
	UserService userService;

	@MockBean
	UserRepository userRepository;

	@MockBean
	RoleRepository roleRepository;

	Optional<User> getUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("testpassword");
		Optional<User> users = Optional.of(user);
		return users;
	}

	Optional<User> getEmptyUser() {
		Optional<User> users = Optional.empty();
		return users;
	}

	Optional<Role> getEmptyRole() {
		Optional<Role> roles = Optional.empty();
		return roles;
	}

	@Test
	@DirtiesContext
	void testAddUserUserNameExists() {
		Optional<User> user = getUser();

		User userToAdd = user.get();
		String username = userToAdd.getUsername();
		String password = userToAdd.getPassword();
		when(userRepository.findById(username)).thenReturn(getUser());

		assertThatExceptionOfType(UsernameExistsException.class).isThrownBy(() -> {

			userService.addUser(username, password);
		});

	}

	@Test
	@DirtiesContext
	void testAddUserRoleDoesntExists() {
		Optional<User> user = getUser();
		User userToAdd = user.get();
		String username = userToAdd.getUsername();
		String password = userToAdd.getPassword();
		when(userRepository.findById(username)).thenReturn(getEmptyUser());
		when(roleRepository.findById(1L)).thenReturn(getEmptyRole());

		assertThatExceptionOfType(RoleNotFoundException.class).isThrownBy(() -> {
			userService.addUser(username, password);
		});

	}

	@Test
	@DirtiesContext
	void testAddUsergenericException() {
		Optional<User> user = getUser();
		User userToAdd = user.get();
		String username = userToAdd.getUsername();
		String password = userToAdd.getPassword();
		when(userRepository.findById(username)).thenThrow(RuntimeException.class);

		assertThatExceptionOfType(UnableToAddNewUserException.class).isThrownBy(() -> {
			userService.addUser(username, password);
		});

	}

}
