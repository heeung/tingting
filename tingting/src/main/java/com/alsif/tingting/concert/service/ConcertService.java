package com.alsif.tingting.concert.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alsif.tingting.concert.dto.ConcertBaseDto;
import com.alsif.tingting.concert.dto.ConcertDetailBaseDto;
import com.alsif.tingting.concert.dto.ConcertDetailResponseDto;
import com.alsif.tingting.concert.dto.ConcertListRequestDto;
import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.concert.dto.performer.PerformerBaseDto;
import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.concert.repository.ConcertDetailRepository;
import com.alsif.tingting.concert.repository.ConcertPerformerRepository;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.global.constant.ErrorCode;
import com.alsif.tingting.global.exception.CustomException;
import com.alsif.tingting.user.repository.UserConcertRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertService {

	private final ConcertRepository concertRepository;
	private final UserConcertRepository userConcertRepository;
	private final ConcertPerformerRepository concertPerformerRepository;
	private final ConcertDetailRepository concertDetailRepository;

	/*
		콘서트 목록 불러오기
	 */
	@Transactional
	public ConcertListResponseDto findConcertList(ConcertListRequestDto requestDto) {

		Pageable pageable = PageRequest.of(requestDto.getCurrentPage() - 1, requestDto.getItemCount());
		Page<ConcertBaseDto> concertBaseDtos;
		LocalDateTime now = LocalDateTime.now();

		String orderBy = requestDto.getOrderBy(); // 예매중: now, 예매임박: soon
		if (orderBy != null) {
			// 1) 예매 중: 예매 시작 시간 < 현재 시간 < 예매 종료 시간 (예매 오픈일 기준 내림차순)
			if (orderBy.equals("now")) {
				concertBaseDtos = concertRepository.findAllConcertNow(now, pageable);
				log.info("예매 중인 콘서트 목록: {}", concertBaseDtos.toString());
				return returnConcertListResponseDto(requestDto, concertBaseDtos);
			}
			// 2) 예매 임박: 현재 시간 < 예매 오픈 시간 (예매 오픈일 기준 오름차순)
			if (orderBy.equals("soon")) {
				concertBaseDtos = concertRepository.findAllConcertSoon(now, pageable);
				log.info("예매 임박 콘서트 목록: {}", concertBaseDtos.toString());
				return returnConcertListResponseDto(requestDto, concertBaseDtos);
			}
			throw new CustomException(ErrorCode.BAD_REQUEST_ORDERBY);
		}

		// 검색 결과 (조건: 장소, 키워드, 콘서트 시작일/종료일)
		String place = requestDto.getPlace();
		String searchWord = requestDto.getSearchWord();
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (requestDto.getStartDate() != null) {
			startDate = LocalDate.parse(requestDto.getStartDate(), formatter).atStartOfDay();
		}
		if (requestDto.getEndDate() != null) {
			endDate = LocalDate.parse(requestDto.getEndDate(), formatter).atStartOfDay().plusDays(1);
		}
		log.info("place: {}, searchWord:{}, startDate: {}, endDate: {}", place, searchWord, startDate, endDate);

		// 1. 장소 O && 키워드 O && 시작일 O && 종료일 O
		if (place != null && searchWord != null && startDate != null && endDate != null) {
			// where 조건: 장소, 키워드, 시작일, 종료일
			concertBaseDtos = concertRepository.findAllConcertByPlaceAndSearchWordAndDate(
				place, searchWord, startDate, endDate, pageable);
			log.info("콘서트 검색 결과 (장소, 키워드, 날짜): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 2. 장소 O && 키워드 O && (시작일 X || 종료일 X)
		if (place != null && searchWord != null) {
			// where 조건: 장소, 키워드
			concertBaseDtos = concertRepository.findAllConcertByPlaceAndSearchWord(place, searchWord, pageable);
			log.info("콘서트 검색 결과 (장소, 키워드): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 3. 장소 O && 키워드 X && 시작일 O && 종료일 O
		if (place != null && startDate != null && endDate != null) {
			// where 조건: 장소, 시작일, 종료일
			concertBaseDtos = concertRepository.findAllConcertByPlaceAndDate(place, startDate, endDate, pageable);
			log.info("콘서트 검색 결과 (장소, 날짜): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 4. 장소 O && 키워드 X && (시작일 X || 종료일 X)
		if (place != null) {
			// where 조건: 장소
			concertBaseDtos = concertRepository.findAllConcertByPlace(place, pageable);
			log.info("콘서트 검색 결과 (장소): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 5. 장소 X && 키워드 O && 시작일 O && 종료일 O
		if (searchWord != null && startDate != null && endDate != null) {
			// where 조건: 키워드, 시작일, 종료일
			concertBaseDtos = concertRepository.findAllConcertBySearchWordAndDate(searchWord, startDate, endDate,
				pageable);
			log.info("콘서트 검색 결과 (키워드, 날짜): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 6. 장소 X && 키워드 O && (시작일 X || 종료일 X)
		if (searchWord != null) {
			// where 조건: 키워드
			concertBaseDtos = concertRepository.findAllConcertBySearchWord(searchWord, pageable);
			log.info("콘서트 검색 결과 (키워드): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 7. 장소 X && 키워드 X && 시작일 O && 종료일 O
		if (startDate != null && endDate != null) {
			// where 조건: 시작일, 종료일
			concertBaseDtos = concertRepository.findAllConcertByDate(startDate, endDate, pageable);
			log.info("콘서트 검색 결과 (날짜): {}", concertBaseDtos.toString());
			return returnConcertListResponseDto(requestDto, concertBaseDtos);
		}
		// 8. 장소 X && 키워드 X && (시작일 X || 종료일 X): 예매 중인 콘서트 정보 그대로 준다.
		concertBaseDtos = concertRepository.findAllConcertNow(now, pageable);
		log.info("콘서트 검색 결과 (조건 없음): {}", concertBaseDtos.toString());
		return returnConcertListResponseDto(requestDto, concertBaseDtos);
	}

	/*
		ConcertListResponseDto 생성
	 */
	private ConcertListResponseDto returnConcertListResponseDto(ConcertListRequestDto requestDto,
		Page<ConcertBaseDto> concertBaseDtos) {
		ConcertListResponseDto concertListResponseDto = ConcertListResponseDto.builder()
			.totalPage(concertBaseDtos.getTotalPages())
			.currentPage(requestDto.getCurrentPage())
			.concerts(concertBaseDtos.getContent())
			.build();
		log.info("콘서트 목록: {}", concertListResponseDto);
		return concertListResponseDto;
	}

	/*
		콘서트 상세 정보 불러오기
	 */
	public ConcertDetailResponseDto findConcertDetail(Long concertSeq, Long userSeq) {

		ConcertDetailResponseDto concertDetailResponseDto
			= concertRepository.findByConcertDetailsByConcertSeq(concertSeq);
		log.info(concertDetailResponseDto.toString());

		boolean favorite = userConcertRepository.existsByUser_SeqAndConcert_Seq(userSeq, concertSeq);
		concertDetailResponseDto.setFavorite(favorite);
		log.info("favorite: {}", favorite);

		List<PerformerBaseDto> performerBaseDtos = concertPerformerRepository.findAllByConcertSeq(concertSeq);
		concertDetailResponseDto.setPerformers(performerBaseDtos);
		log.info(performerBaseDtos.toString());

		List<ConcertDetailBaseDto> concertDetailBaseDtos = concertDetailRepository.findAllByConcert_Seq(concertSeq)
			.stream()
			.map(ConcertDetail::convertToConcertDetailBaseDto)
			.collect(Collectors.toList());
		concertDetailResponseDto.setConcertDetails(concertDetailBaseDtos);
		log.info(concertDetailBaseDtos.toString());

		return concertDetailResponseDto;
	}
}
