package com.alsif.tingting.global.constant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	BAD_REQUEST_ORDERBY(HttpStatus.BAD_REQUEST, "잘못된 매개변수 요청");

	private final HttpStatus httpStatus;
	private final String message;
}
