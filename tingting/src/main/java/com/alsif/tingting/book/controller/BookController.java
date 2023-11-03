package com.alsif.tingting.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.book.service.BookService;
import com.alsif.tingting.concert.dto.concerthall.ConcertHallPatternResponseDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSeatBookRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoResponseDto;
import com.alsif.tingting.concert.dto.concerthall.SuccessResponseDto;
import com.alsif.tingting.global.dto.ErrorResponseDto;

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
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "콘서트 예매", description = "콘서트 예매 API")
public class BookController {

	private final BookService bookService;

	@Operation(summary = "콘서트장 정보 조회")
	@Parameters(value = {
		@Parameter(required = true, name = "concertSeq", description = "콘서트 PK")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{concertSeq}")
	ResponseEntity<ConcertHallPatternResponseDto> findConcertPattern(
		@PathVariable("concertSeq") Integer concertSeq) {
		log.info("===== 콘서트장 정보 조회 요청 시작, url={}, concertSeq: {} =====",
			"/concerts", concertSeq);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertHallPatternResponseDto concertHallPatternResponseDto = bookService.findConcertPattern(concertSeq);
		stopWatch.stop();

		log.info("===== 콘서트 정보 조회 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertHallPatternResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "콘서트장 섹션별 좌석 정보 조회")
	@Parameters(value = {
		@Parameter(required = true, name = "concertDetailSeq", description = "콘서트 상세 PK (ex. 1)"),
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{concertDetailSeq}/section")
	ResponseEntity<List<ConcertSectionSeatInfoResponseDto>> findConcertSectionSeatInfoList(
		@PathVariable("concertDetailSeq") Integer concertDetailSeq, ConcertSectionSeatInfoRequestDto requestDto) {
		log.info("===== 콘서트장 섹션별 좌석 정보 조회 요청 시작, url={}, concertDetailSeq: {}, {} =====",
			"/concerts", concertDetailSeq, requestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<ConcertSectionSeatInfoResponseDto> concertSectionSeatInfoResponseDtos
			= bookService.findConcertSectionSeatInfoList(concertDetailSeq, requestDto);
		stopWatch.stop();

		log.info("===== 콘서트장 섹션별 좌석 정보 조회 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertSectionSeatInfoResponseDtos, HttpStatus.OK);
	}

	@Operation(summary = "선택 좌석의 예매 가능 여부 확인")
	@Parameters(value = {
		@Parameter(required = true, name = "concertDetailSeq", description = "콘서트 상세 PK (ex. 1)"),
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "409", description = "선택한 좌석 목록에 이미 예약된 좌석이 포함되어 있음",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{concertDetailSeq}/seat")
	ResponseEntity<SuccessResponseDto> isSeatAvailable(
		@PathVariable("concertDetailSeq") Integer concertDetailSeq, ConcertSeatBookRequestDto requestDto) {
		log.info("===== 선택 좌석의 예매 가능 여부 확인 요청 시작, url={}, concertDetailSeq: {}, {} =====",
			"/concerts", concertDetailSeq, requestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SuccessResponseDto successResponseDto
			= bookService.isSeatAvailable(concertDetailSeq, requestDto);
		stopWatch.stop();

		log.info("===== 선택 좌석의 예매 가능 여부 확인 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "선택 좌석 예매 요청")
	@Parameters(value = {
		@Parameter(required = true, name = "concertDetailSeq", description = "콘서트 상세 PK (ex. 1)"),
		@Parameter(required = true, name = "userSeq", description = "회원 PK (ex. 1)")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "401", description = "등록되지 않은 회원",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "403", description = "포인트 부족",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "409", description = "선택한 좌석 목록에 이미 예약된 좌석이 포함되어 있음",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@PostMapping("/{concertDetailSeq}/seat")
	ResponseEntity<SuccessResponseDto> book(
		@PathVariable("concertDetailSeq") Integer concertDetailSeq, @RequestParam Integer userSeq,
		@RequestBody ConcertSeatBookRequestDto requestDto) {
		log.info("===== 선택 좌석 예매 요청 시작, url={}, concertDetailSeq: {}, {} =====",
			"/concerts", concertDetailSeq, requestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SuccessResponseDto successResponseDto
			= bookService.book(userSeq, concertDetailSeq, requestDto);
		stopWatch.stop();

		log.info("===== 선택 좌석 예매 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "예매 취소")
	@Parameters(value = {
		@Parameter(required = true, name = "ticketSeq", description = "예매 PK (ex. 317)"),
		@Parameter(required = true, name = "userSeq", description = "회원 PK (ex. 1)")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "401", description = "등록되지 않은 회원",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "403", description = "권한이 없는 회원",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		@ApiResponse(responseCode = "409", description = "이미 예매가 취소된 티켓",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@DeleteMapping("/{ticketSeq}")
	ResponseEntity<SuccessResponseDto> reservationCancellation(@PathVariable("ticketSeq") Integer ticketSeq,
		@RequestParam Integer userSeq) {
		log.info("===== 콘서트 예매 취소 요청 시작, url={}, userSeq: {}, ticketSeq: {} =====",
			"/concerts", userSeq, ticketSeq);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SuccessResponseDto successResponseDto = bookService.reservationCancellation(userSeq, ticketSeq);
		stopWatch.stop();

		log.info("===== 콘서트 예매 취소 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
	}
}
