package com.cloudshop.exceptionhandler.exceptions.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@SuppressWarnings("unused")
public class Parent {
	private String message;
	private int errorCode;
	private List<Child> childErrors;
}
