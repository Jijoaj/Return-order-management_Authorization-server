package com.authorization.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.authorization.model.User;

@SpringBootTest
class UserServiceTest {

	@Autowired
	UserService userService;

	Optional<User> getUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("testpassword");
		Optional<User> users = Optional.of(user);
		return users;
	}

	@Test
	@DirtiesContext
	void testAddUser() {
		Optional<User> user = getUser();
		User userToAdd = user.get();
		ResponseEntity<String> response = userService.addUser(userToAdd.getUsername(), userToAdd.getPassword());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("user added successfully", response.getBody());

	}

}
