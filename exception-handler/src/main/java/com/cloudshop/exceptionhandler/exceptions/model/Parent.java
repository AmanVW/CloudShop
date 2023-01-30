package com.cloudshop.exceptionhandler.exceptions.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@SuppressWarnings("unused")
public class Parent {
	private String message;
	private int errorCode;
	private Child child;
}
