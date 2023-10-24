package com.alsif.tingting.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.user.dto.FavoriteListRequestDto;
import com.alsif.tingting.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
		@Parameter(required = true, name = "userSeq", description = "회원 PK")
	})
	@GetMapping("/{userSeq}/favorite")
	ResponseEntity<ConcertListResponseDto> findFavoriteList(@PathVariable("userSeq") Long userSeq,
		FavoriteListRequestDto favoriteListRequestDto) {
		log.info("===== 콘서트 찜 목록 요청 시작, url={}, userSeq: {}, {} =====",
			"/concerts", userSeq, favoriteListRequestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertListResponseDto concertListResponseDto = userService.findFavoriteList(userSeq, favoriteListRequestDto);
		stopWatch.stop();

		log.info("===== 콘서트 찜 목록 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertListResponseDto, HttpStatus.OK);
	}
}
