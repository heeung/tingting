package com.alsif.tingting.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.global.dto.ErrorResponseDto;
import com.alsif.tingting.global.dto.PageableDto;
import com.alsif.tingting.user.dto.TicketListResponseDto;
import com.alsif.tingting.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 탈퇴/마이페이지", description = "회원 API")
public class UserController {

	private final UserService userService;

	@Operation(summary = "찜 목록")
	@Parameters(value = {
		@Parameter(required = true, name = "userSeq", description = "회원 PK (ex. 1)")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", description = "등록되지 않은 회원",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{userSeq}/favorite")
	ResponseEntity<ConcertListResponseDto> findFavoriteList(@PathVariable("userSeq") Integer userSeq,
		PageableDto pageableDto) {
		log.info("===== 콘서트 찜 목록 요청 시작, url={}, userSeq: {}, {} =====",
			"/concerts", userSeq, pageableDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertListResponseDto concertListResponseDto = userService.findFavoriteList(userSeq, pageableDto);
		stopWatch.stop();

		log.info("===== 콘서트 찜 목록 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertListResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "예매 내역")
	@Parameters(value = {
		@Parameter(required = true, name = "userSeq", description = "회원 PK (ex. 435)")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", description = "등록되지 않은 회원",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{userSeq}/ticket")
	ResponseEntity<TicketListResponseDto> findTicketList(@PathVariable("userSeq") Integer userSeq,
		PageableDto pageableDto) {
		log.info("===== 콘서트 예매 내역 요청 시작, url={}, userSeq: {}, {} =====",
			"/concerts", userSeq, pageableDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TicketListResponseDto ticketListResponseDto = userService.findTicketList(userSeq, pageableDto);
		stopWatch.stop();

		log.info("===== 콘서트 예매 내역 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(ticketListResponseDto, HttpStatus.OK);
	}
}
