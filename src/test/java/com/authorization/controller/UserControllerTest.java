package com.authorization.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.authorization.exceptions.RoleNotFoundException;
import com.authorization.exceptions.UnableToAddNewUserException;
import com.authorization.exceptions.UsernameExistsException;
import com.authorization.services.UserService;

@SpringBootTest

@AutoConfigureMockMvc
class UserControllerTest {

	private static final String PASSWORD = "password";

	private static final String USERNAME = "username";

	private static final String TESTPASSWORD = "testpassword";

	private static final String TESTUSER = "testuser";

	@MockBean
	UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testUserController() throws Exception {

		when(userService.addUser(TESTUSER, TESTPASSWORD))
				.thenReturn(new ResponseEntity<>("user added successfully", HttpStatus.CREATED));
		this.mockMvc.perform(post("/signup").param(USERNAME, TESTUSER).param(PASSWORD, TESTPASSWORD)).andDo(print())
				.andExpect(status().isCreated()).andExpect(content().string("user added successfully"));

	}

	@Test
	void testUserControllerRoleNotFoundException() throws Exception {

		when(userService.addUser(TESTUSER, TESTPASSWORD)).thenThrow(RoleNotFoundException.class);
		this.mockMvc.perform(post("/signup").param(USERNAME, TESTUSER).param(PASSWORD, TESTPASSWORD)).andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("unable to assign a role to user"));

	}

	@Test
	void testUserControllerUnableToAddNewUserException() throws Exception {

		when(userService.addUser(TESTUSER, TESTPASSWORD)).thenThrow(UnableToAddNewUserException.class);
		this.mockMvc.perform(post("/signup").param(USERNAME, TESTUSER).param(PASSWORD, TESTPASSWORD)).andDo(print())
				.andExpect(status().isTemporaryRedirect())
				.andExpect(jsonPath("$.message").value("an unknown error occured while adding new user"));

	}

	@Test
	void testUserControllerUsernameExistsException() throws Exception {

		when(userService.addUser(TESTUSER, TESTPASSWORD)).thenThrow(UsernameExistsException.class);
		this.mockMvc.perform(post("/signup").param(USERNAME, TESTUSER).param(PASSWORD, TESTPASSWORD)).andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("username already exists, create new one"));

	}

	@Test
	void testUserControllerValidation() throws Exception {

		this.mockMvc.perform(post("/signup").param(USERNAME, "test").param(PASSWORD, TESTPASSWORD)).andDo(print())
				.andExpect(status().isBadRequest());

	}

}
