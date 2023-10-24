package com.alsif.tingting.concert.controller;

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

import com.alsif.tingting.concert.dto.ConcertDetailResponseDto;
import com.alsif.tingting.concert.dto.ConcertFavoriteRequestDto;
import com.alsif.tingting.concert.dto.ConcertFavoriteResponseDto;
import com.alsif.tingting.concert.dto.ConcertListRequestDto;
import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.concert.service.ConcertService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
		log.info("===== 콘서트 목록 불러오기 요청 시작, url={}, {} =====",
			"/concerts", concertListRequestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertListResponseDto concertListResponseDto = concertService.findConcertList(concertListRequestDto);
		stopWatch.stop();

		log.info("===== 콘서트 목록 불러오기 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertListResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "콘서트 상세 정보")
	@Parameters(value = {
		@Parameter(required = true, name = "concertSeq", description = "콘서트 PK"),
		@Parameter(required = true, name = "userSeq", description = "회원 PK")
	})
	@GetMapping("/{concertSeq}")
	ResponseEntity<ConcertDetailResponseDto> findConcertDetail(@PathVariable("concertSeq") Long concertSeq,
		@RequestParam Long userSeq) {
		log.info("===== 콘서트 상세 정보 불러오기 요청 시작, url={}, concertSeq: {}, userSeq: {} =====",
			"/concerts", concertSeq, userSeq);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertDetailResponseDto concertDetailResponseDto = concertService.findConcertDetail(concertSeq, userSeq);
		stopWatch.stop();

		log.info("===== 콘서트 상세 정보 불러오기 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertDetailResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "콘서트 찜하기")
	@PostMapping("/favorite")
	ResponseEntity<ConcertFavoriteResponseDto> addToFavorites(
		@RequestBody ConcertFavoriteRequestDto concertFavoriteRequestDto) {
		log.info("===== 콘서트 찜하기 요청 시작, url={}, {} =====",
			"/concerts", concertFavoriteRequestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertFavoriteResponseDto concertFavoriteResponseDto = concertService.addToFavorites(
			concertFavoriteRequestDto);
		stopWatch.stop();

		log.info("===== 콘서트 찜하기 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertFavoriteResponseDto, HttpStatus.OK);
	}
}
