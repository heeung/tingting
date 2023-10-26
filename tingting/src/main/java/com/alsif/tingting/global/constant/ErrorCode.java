package com.alsif.tingting.global.constant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	BAD_REQUEST_ORDERBY(HttpStatus.BAD_REQUEST, "잘못된 매개변수(orderBy) 요청"),
	BAD_REQUEST_CONCERT_SEQ(HttpStatus.BAD_REQUEST, "잘못된 매개변수(concertSeq) 요청"),
	BAD_REQUEST_CONCERT_SECTION_INFO(HttpStatus.BAD_REQUEST, "잘못된 매개변수(concertDetailSeq, concertHallSeq, target) 요청");

	private final HttpStatus httpStatus;
	private final String message;
}
