package com.alsif.tingting.concert.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.concert.dto.ConcertListRequestDto;
import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.concert.service.ConcertService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/concerts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "콘서트 목록/상세/찜", description = "콘서트 API")
public class ConcertController {

	private final ConcertService concertService;

	@Operation(summary = "콘서트 목록", description = "예매중, 예매임박, 검색")
	@GetMapping("")
	ResponseEntity<ConcertListResponseDto> findConcertList(ConcertListRequestDto concertListRequestDto) {
		log.info("===== 콘서트 목록 불러오기 요청 시작, url={}, concertListRequestDto {} =====",
			"/concerts", concertListRequestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertListResponseDto concertListResponseDto = concertService.findConcertList(concertListRequestDto);
		stopWatch.stop();

		log.info("===== 콘서트 목록 불러오기 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertListResponseDto, HttpStatus.OK);
	}
}
