package com.cloudshop.exceptionhandler.exceptions;

@SuppressWarnings("serial")
public class UsernameAlreadyExistsException extends RuntimeException{
	
	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

}
