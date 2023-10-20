package com.alsif.tingting.concert.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alsif.tingting.concert.dto.ConcertBaseDto;
import com.alsif.tingting.concert.dto.ConcertListRequestDto;
import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.global.constant.ErrorCode;
import com.alsif.tingting.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertService {

	private final ConcertRepository concertRepository;

	/*
		콘서트 목록 불러오기
	 */
	@Transactional
	public ConcertListResponseDto findConcertList(ConcertListRequestDto requestDto) {

		Pageable pageable = PageRequest.of(requestDto.getCurrentPage() - 1, requestDto.getItemCount());
		Page<ConcertBaseDto> concertBaseDtos = null;

		String orderBy = requestDto.getOrderBy(); // 예매중: now, 예매임박: soon
		if (orderBy != null) {
			LocalDateTime now = LocalDateTime.now();

			if (orderBy.equals("now")) {
				// 1) 예매 중: 예매 시작 시간 < 현재 시간 < 예매 종료 시간 (예매 오픈일 기준 내림차순)
				concertBaseDtos = concertRepository.findAllConcertNow(now, pageable);
				log.info("예매 중인 콘서트 목록: {}", concertBaseDtos.toString());
			} else if (orderBy.equals("soon")) {
				// 2) 예매 임박: 현재 시간 < 예매 오픈 시간 (예매 오픈일 기준 오름차순)
				concertBaseDtos = concertRepository.findAllConcertSoon(now, pageable);
				log.info("예매 임박 콘서트 목록: {}", concertBaseDtos.toString());
			} else {
				throw new CustomException(ErrorCode.BAD_REQUEST_ORDERBY);
			}
		}

		if (orderBy == null) {
			// 검색 결과 반환 로직 구현 예정
			return null;
		}

		return ConcertListResponseDto.builder()
			.totalPage(concertBaseDtos.getTotalPages())
			.currentPage(requestDto.getCurrentPage())
			.concerts(concertBaseDtos.getContent())
			.build();
	}
}
