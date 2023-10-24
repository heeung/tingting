package com.alsif.tingting.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alsif.tingting.concert.dto.ConcertBaseDto;
import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.user.dto.FavoriteListRequestDto;
import com.alsif.tingting.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final ConcertRepository concertRepository;

	/*
		찜 목록 가져오기
	 */
	public ConcertListResponseDto findFavoriteList(Long userSeq, FavoriteListRequestDto requestDto) {
		Pageable pageable = PageRequest.of(requestDto.getCurrentPage() - 1, requestDto.getItemCount());
		Page<ConcertBaseDto> concertBaseDtos = concertRepository.findAllConcertFavorite(userSeq, pageable);
		log.info("찜 중인 콘서트 개수: {}", concertBaseDtos.getContent().size());
		return ConcertListResponseDto.builder()
			.totalPage(concertBaseDtos.getTotalPages())
			.currentPage(requestDto.getCurrentPage())
			.concerts(concertBaseDtos.getContent())
			.build();
	}
}
