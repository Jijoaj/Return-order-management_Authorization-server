package com.authorization.exceptions;

public class UsernameExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3712708547423132954L;

	public UsernameExistsException(String message) {
		super(message);
	}

}
