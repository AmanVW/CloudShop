package com.cloudshop.exceptionhandler;

import java.util.*;
import java.util.stream.Collectors;

import com.cloudshop.exceptionhandler.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cloudshop.exceptionhandler.exceptions.model.Child;
import com.cloudshop.exceptionhandler.exceptions.model.Parent;
import org.springframework.web.context.request.WebRequest;

@ComponentScan(basePackages = {"com.cloudshop"})
@ControllerAdvice(basePackages = {"com.cloudshop"})
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<Parent> usernameAlreadyExistsException(Exception exception, HttpServletRequest request) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(TokenNotProvidedException.class)
	public ResponseEntity<Parent> tokenNotProvidedException(Exception exception, HttpServletRequest request) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Parent> emailAlreadyExistsException(Exception exception, HttpServletRequest request) {

		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<Parent> roleNotFoundException(Exception exception, HttpServletRequest request) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Parent> userNotFoundException(Exception exception, HttpServletRequest request) {

		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<Parent> unauthorizedAccessException(Exception exception, HttpServletRequest request) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED, request);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Parent> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {

		return prepareError(exception.getBindingResult().getFieldErrors()
				.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(BadCredentialsCustomException.class)
	public ResponseEntity<Parent> badCredentialsCustomException(BadCredentialsCustomException exception, HttpServletRequest request) {

		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Parent> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED, request);
	}

	private ResponseEntity<Parent> prepareError(List<String> errorMessages, HttpStatus badRequest, HttpServletRequest request) {
		List<Child> childErrors = new ArrayList<>();

		for (String errorMessage : errorMessages) {
			childErrors.add(buildChildError(errorMessage, badRequest.value(), request));
		}

		Parent parent = buildParentError(badRequest.value(), childErrors);

		return new ResponseEntity<>(parent, badRequest);
	}
	
	private Parent buildParentError(int errorCode, List<Child> childErrors) {

        return Parent.builder()
                .message("Unsuccessful Operation")
                .errorCode(errorCode)
                .childErrors(childErrors).build();
    }

	private Child buildChildError(String message, int errorCode, HttpServletRequest request) {
		return Child.builder()
				.message(message)
				.errorCode(errorCode)
				.timestamp(new Date())
				.path(request.getRequestURI())
				.developerEmail("aman@gmail.com")
				.build();
	}
}
