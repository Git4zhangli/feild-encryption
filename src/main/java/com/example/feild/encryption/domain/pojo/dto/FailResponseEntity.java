package com.example.feild.encryption.domain.pojo.dto;

public final class FailResponseEntity<T> extends ResponseEntity<T> {
	public FailResponseEntity(int code, String message) {
		super(code, message, null);
	}
	
	public FailResponseEntity(int code, String message, T t) {
		super(code, message, t);
	}
}
