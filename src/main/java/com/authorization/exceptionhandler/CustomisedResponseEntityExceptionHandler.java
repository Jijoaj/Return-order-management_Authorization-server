package com.authorization.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.authorization.exceptions.RoleNotFoundException;
import com.authorization.exceptions.UnableToAddNewUserException;
import com.authorization.exceptions.UsernameExistsException;

@ControllerAdvice
@RestController
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String USERNAME_ALREADY_EXISTS_CREATE_NEW_ONE = "username already exists, create new one";
	private static final String AN_UNKNOWN_ERROR_OCCURED_WHILE_ADDING_NEW_USER = "an unknown error occured while adding new user";
	private static final String UNABLE_TO_ASSIGN_A_ROLE_TO_USER = "unable to assign a role to user";

	@ExceptionHandler(RoleNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleRoleNotFoundException(RoleNotFoundException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), UNABLE_TO_ASSIGN_A_ROLE_TO_USER);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnableToAddNewUserException.class)
	public final ResponseEntity<ExceptionResponse> handleUnableToAddNewUserException(UnableToAddNewUserException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				AN_UNKNOWN_ERROR_OCCURED_WHILE_ADDING_NEW_USER);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.TEMPORARY_REDIRECT);
	}

	@ExceptionHandler(UsernameExistsException.class)
	public final ResponseEntity<ExceptionResponse> handleUsernameExistsException(UsernameExistsException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), USERNAME_ALREADY_EXISTS_CREATE_NEW_ONE);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public final ResponseEntity<ExceptionResponse> handleConstraintViolationException(
			javax.validation.ConstraintViolationException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
