package com.alsif.book.global.exception;

import com.alsif.book.global.constant.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
	ErrorCode errorCode;
}
