package com.authorization.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authorization.exceptions.RoleNotFoundException;
import com.authorization.exceptions.UnableToAddNewUserException;
import com.authorization.exceptions.UsernameExistsException;
import com.authorization.model.Role;
import com.authorization.model.User;
import com.authorization.repository.RoleRepository;
import com.authorization.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	public ResponseEntity<String> addUser(String username, String password) {
		try {

			log.info("adding new user with username {}", username);
			Optional<User> existingUser = userRepository.findById(username);
			log.info("checking whether there is an existing user with username {}", username);
			if (existingUser.isPresent()) {
				log.error("user with this username already exists");
				throw new UsernameExistsException(username);
			} else {
				User user = new User();
				user.setUsername(username);
				user.setPassword(encoder.encode(password));
				log.info("new user object set");
				Optional<Role> normalUserRole = roleRepository.findById(2L);
				if (normalUserRole.isPresent()) {
					Role role = normalUserRole.get();
					log.info("adding default role {} to the user", role);
					Set<Role> userRole = new HashSet<>();
					userRole.add(role);
					user.setRoles(userRole);
					log.info("saving user details to the database");
					userRepository.save(user);
					return new ResponseEntity<>("user added successfully", HttpStatus.CREATED);
				} else {
					log.error("unable to fetch default role details");
					throw new RoleNotFoundException();
				}
			}

		} catch (UsernameExistsException | RoleNotFoundException e) {
			throw e;
		}

		catch (Exception e) {
			log.error("adding user with username {} failed", username);
			throw new UnableToAddNewUserException();
		}
	}
}
