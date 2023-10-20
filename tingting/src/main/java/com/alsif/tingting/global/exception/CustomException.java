package com.alsif.tingting.global.exception;

import com.alsif.tingting.global.constant.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
	ErrorCode errorCode;
}
