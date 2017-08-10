package com.example.feild.encryption.domain.pojo.dto;

import org.springframework.http.HttpStatus;

public final class SuccessResponseEntity<T> extends ResponseEntity<T> {
	public SuccessResponseEntity() {
		super(HttpStatus.OK.value(), "OK", null);
	}
    public SuccessResponseEntity(T data) {
    	super(HttpStatus.OK.value(), "OK", data);
    }
}
