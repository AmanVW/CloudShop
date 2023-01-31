package com.cloudshop.exceptionhandler;

import java.util.*;
import java.util.stream.Collectors;

import com.cloudshop.exceptionhandler.exceptions.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cloudshop.exceptionhandler.exceptions.model.Child;
import com.cloudshop.exceptionhandler.exceptions.model.Parent;

@ComponentScan(basePackages = {"com.cloudshop"})
@ControllerAdvice(basePackages = {"com.cloudshop"})
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<Parent> usernameAlreadyExistsException(Exception exception) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TokenNotProvidedException.class)
	public ResponseEntity<Parent> tokenNotProvidedException(Exception exception) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Parent> emailAlreadyExistsException(Exception exception) {

		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<Parent> roleNotFoundException(Exception exception) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Parent> userNotFoundException(Exception exception) {

		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<Parent> unauthorizedAccessException(Exception exception) {
		return prepareError(Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Parent> methodArgumentNotValidException(MethodArgumentNotValidException exception) {

		return prepareError(exception.getBindingResult().getFieldErrors()
				.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Parent> prepareError(List<String> errorMessages, HttpStatus badRequest) {
		List<Child> childErrors = new ArrayList<>();

		for (String errorMessage : errorMessages) {
			childErrors.add(buildChildError(errorMessage, badRequest.value()));
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

	private Child buildChildError(String message, int errorCode) {
		return Child.builder()
				.message(message)
				.errorCode(errorCode)
				.timestamp(new Date())
				.developerEmail("aman@gmail.com")
				.build();
	}
}
