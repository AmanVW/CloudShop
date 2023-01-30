package com.cloudshop.exceptionhandler;

import java.util.Date;

import com.cloudshop.exceptionhandler.exceptions.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.cloudshop.exceptionhandler.exceptions.model.Child;
import com.cloudshop.exceptionhandler.exceptions.model.Parent;

@ComponentScan(basePackages = {"com.cloudshop"})
@ControllerAdvice(basePackages = {"com.cloudshop"})
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<Parent> usernameAlreadyExistsException(Exception exception, WebRequest webRequest) {
		Parent parent = prepareErrorResponse(exception.getLocalizedMessage(), 400);
		return new ResponseEntity<>(parent, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TokenNotProvidedException.class)
	public ResponseEntity<Parent> tokenNotProvidedException(Exception exception, WebRequest webRequest) {
		Parent parent = prepareErrorResponse(exception.getLocalizedMessage(), 400);
		return new ResponseEntity<>(parent, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Parent> emailAlreadyExistsException(Exception exception, WebRequest webRequest) {
		Parent parent = prepareErrorResponse(exception.getLocalizedMessage(), 400);
		return new ResponseEntity<>(parent, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<Parent> roleNotFoundException(Exception exception, WebRequest webRequest) {
		Parent parent = prepareErrorResponse(exception.getLocalizedMessage(), 400);
		return new ResponseEntity<>(parent, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Parent> userNotFoundException(Exception exception, WebRequest webRequest) {
		Parent parent = prepareErrorResponse(exception.getLocalizedMessage(), 400);
		return new ResponseEntity<>(parent, HttpStatus.BAD_REQUEST);
	}
	
	private Parent prepareErrorResponse(String message, int errorCode) {

        Child child = Child.builder()
                .message(message)
                .errorCode(errorCode)
                .timestamp(new Date())
                .developerEmail("aman@gmail.com")
                .build();

        return Parent.builder()
                .message("BadRequest")
                .errorCode(errorCode)
                .child(child).build();
    }
}
