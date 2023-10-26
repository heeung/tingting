package com.alsif.tingting.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alsif.tingting.concert.dto.concerthall.ConcertHallPatternResponseDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoResponseDto;
import com.alsif.tingting.concert.entity.Concert;
import com.alsif.tingting.concert.repository.ConcertHallSeatRepository;
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
	private final ConcertHallSeatRepository concertHallSeatRepository;

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

	/*
		콘서트장 섹션별 좌석 정보 조회
	 */
	public List<ConcertSectionSeatInfoResponseDto> findConcertSectionSeatInfoList(Long concertDetailSeq,
		ConcertSectionSeatInfoRequestDto requestDto) {

		List<ConcertSectionSeatInfoResponseDto> concertSectionSeatInfoResponseDtos
			= concertHallSeatRepository.findAllSectionSeatInfo(concertDetailSeq, requestDto.getConcertHallSeq(),
			requestDto.getTarget());

		if (concertSectionSeatInfoResponseDtos.size() == 0) {
			throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_SECTION_INFO);
		}

		log.info("{} 섹션의 조회된 좌석 개수: {}", requestDto.getTarget(), concertSectionSeatInfoResponseDtos.size());
		return concertSectionSeatInfoResponseDtos;
	}
}
