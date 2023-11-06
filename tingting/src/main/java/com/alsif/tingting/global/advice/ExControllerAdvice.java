package com.alsif.tingting.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alsif.tingting.global.dto.ErrorResponseDto;
import com.alsif.tingting.global.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {
	@ExceptionHandler
	public ResponseEntity<ErrorResponseDto> customErrorHandler(CustomException ex) {
		log.info(ex.getErrorCode().getMessage());
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode().getMessage());
		return new ResponseEntity<>(errorResponseDto, ex.getErrorCode().getHttpStatus());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponseDto> serverErrorHandler(Exception ex) {
		ex.printStackTrace();
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage());
		return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
