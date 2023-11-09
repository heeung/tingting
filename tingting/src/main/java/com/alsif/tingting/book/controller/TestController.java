package com.alsif.tingting.book.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.book.service.BookService;
import com.alsif.tingting.concert.dto.concerthall.ConcertSeatBookRequestDto;
import com.alsif.tingting.concert.dto.concerthall.SuccessResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book-test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

	private final BookService bookService;

	@PostMapping("/{concertDetailSeq}/seat")
	ResponseEntity<SuccessResponseDto> book(
		@PathVariable("concertDetailSeq") Integer concertDetailSeq, @RequestParam Integer userSeq,
		@RequestBody ConcertSeatBookRequestDto requestDto) {
		log.info("===== 선택 좌석 예매 요청 시작, url={}, concertDetailSeq: {}, {} =====",
			"/concerts", concertDetailSeq, requestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SuccessResponseDto successResponseDto
			= bookService.bookTest(userSeq, concertDetailSeq, requestDto);
		stopWatch.stop();

		log.info("===== 선택 좌석 예매 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
	}

}
