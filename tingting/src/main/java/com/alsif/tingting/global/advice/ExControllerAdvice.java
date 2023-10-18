package com.alsif.tingting.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alsif.tingting.global.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<ErrorResponseDto> serverErrorHandler(Exception ex) {
		ex.printStackTrace();
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage());
		return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
