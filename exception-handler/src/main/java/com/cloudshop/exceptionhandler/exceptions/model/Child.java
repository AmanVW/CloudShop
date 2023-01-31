package com.cloudshop.exceptionhandler.exceptions.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Child {
	private String message;
	private int errorCode;
	private Date timestamp;
	private String path;
	private String developerEmail;
}
