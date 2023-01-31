package com.cloudshop.exceptionhandler.exceptions;

public class BadCredentialsCustomException extends RuntimeException{
    public BadCredentialsCustomException(String message) {
        super(message);
    }
}
