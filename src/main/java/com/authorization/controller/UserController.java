package com.authorization.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.services.UserService;

@RestController
@Validated
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(
			@NotBlank(message = "username cant be blank") @Size(min = 5, max = 15, message = "username should have a length between 5 and 15") @Pattern(regexp = "[a-zA-Z]*", message = "username contain alphabets only") @RequestParam String username,
			@NotBlank(message = "password cant be blank") @Size(min = 6, max = 20, message = "password should have a length between 6 and 20") @RequestParam String password) {
		return userService.addUser(username, password);
	}

}
