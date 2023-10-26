package com.alsif.tingting.book.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alsif.tingting.concert.dto.concerthall.ConcertHallPatternResponseDto;
import com.alsif.tingting.concert.entity.Concert;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.global.constant.ErrorCode;
import com.alsif.tingting.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

	private final ConcertRepository concertRepository;

	/*
		콘서트장 정보 조회
	 */
	public ConcertHallPatternResponseDto findConcertPattern(Long concertSeq) {
		Optional<Concert> concert = concertRepository.findById(concertSeq);
		if (concert.isEmpty()) {
			throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_SEQ);
		}

		log.info(concert.toString());
		return ConcertHallPatternResponseDto.builder()
			.concertHallSeq(concert.get().getConcertHall().getSeq())
			.pattern(concert.get().getConcertHall().getPattern())
			.build();
	}
}
