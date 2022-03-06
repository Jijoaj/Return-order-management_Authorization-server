package com.authorization.exceptionhandler;

import java.util.Date;

import lombok.Getter;

@Getter
public class ExceptionResponse {
	private Date timeStamp;
	private String message;

	public ExceptionResponse(Date timeStamp, String message) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
	}

}
