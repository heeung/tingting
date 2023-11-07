package com.alsif.tingting.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alsif.tingting.book.dto.TicketBaseDto;
import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.dto.ConcertBaseDto;
import com.alsif.tingting.concert.dto.ConcertListResponseDto;
import com.alsif.tingting.concert.dto.concerthall.SeatBaseDto;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.global.constant.ErrorCode;
import com.alsif.tingting.global.dto.PageableDto;
import com.alsif.tingting.global.exception.CustomException;
import com.alsif.tingting.user.dto.LoginResponseDto;
import com.alsif.tingting.user.dto.TicketListResponseDto;
import com.alsif.tingting.user.entity.User;
import com.alsif.tingting.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final ConcertRepository concertRepository;
	private final TicketRepository ticketRepository;
	private final TicketSeatRepository ticketSeatRepository;

	@Value("${spring.kakao.client_id}")
	private String clientId;

	@Value("${spring.kakao.redirect_uri")
	private String redirectUri;
	/*
		찜 목록 가져오기
	 */
	public ConcertListResponseDto findFavoriteList(Integer userSeq, PageableDto requestDto) {

		userRepository.findById(userSeq).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));

		Pageable pageable = PageRequest.of(requestDto.getCurrentPage() - 1, requestDto.getItemCount());
		Page<ConcertBaseDto> concertBaseDtos = concertRepository.findAllConcertFavorite(userSeq, pageable);
		log.info("찜 중인 콘서트 개수: {}", concertBaseDtos.getContent().size());

		return ConcertListResponseDto.builder()
			.totalPage(concertBaseDtos.getTotalPages())
			.currentPage(requestDto.getCurrentPage())
			.concerts(concertBaseDtos.getContent())
			.build();
	}

	/*
		예매 내역 가져오기
	 */
	public TicketListResponseDto findTicketList(Integer userSeq, PageableDto requestDto) {

		userRepository.findById(userSeq).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));

		// 1. 회원이 가지고 있는 모든 예매 내역 조회
		Pageable pageable = PageRequest.of(requestDto.getCurrentPage() - 1, requestDto.getItemCount());
		Page<TicketBaseDto> tickets = ticketRepository.findAllByUserSeq(userSeq, pageable);

		List<SeatBaseDto> seatBaseDtos = ticketSeatRepository.findAllPriceByTicketSeq(
			tickets.stream().map(TicketBaseDto::getTicketSeq).collect(Collectors.toList()));

		for (SeatBaseDto seatBaseDto : seatBaseDtos) {
			for (TicketBaseDto ticket : tickets) {
				if (ticket.getTicketSeq().equals(seatBaseDto.getTicketSeq())) {
					if (ticket.getSeats() == null) {
						ticket.setSeats(new ArrayList<>());
					}
					ticket.getSeats().add(seatBaseDto);
					break;
				}
			}
		}

		log.info("예매한 콘서트 개수 (취소 내역 포함, 현재 페이지 개수): {}", tickets.getContent().size());

		return TicketListResponseDto.builder()
			.totalPage(tickets.getTotalPages())
			.currentPage(requestDto.getCurrentPage())
			.tickets(tickets.getContent())
			.build();
	}



	public String getKaKaoAccessToken(String code) throws JsonProcessingException {
		String REQUEST_URL = "https://kauth.kakao.com/oauth/token";
		RestTemplate restTemplate = new RestTemplate();

		// Set Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// Set parameter
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);

		// Set http entity
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<String> stringResponseEntity = null;

		stringResponseEntity = restTemplate.postForEntity(REQUEST_URL, request, String.class);


		// JSON 문자열을 ObjectMapper를 사용하여 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(stringResponseEntity.getBody());

		// access_token 추출
		String accessToken = responseJson.get("access_token").asText();

		return accessToken;
	}

	public LoginResponseDto createKakaoUser(String accessToken) throws JsonProcessingException {
		// 해당 accessToken으로 정보 받아오기
		String REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
		RestTemplate restTemplate = new RestTemplate();

		// Set Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("Authorization", "Bearer " + accessToken);

		// Set http entity
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
		ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(REQUEST_URL, request, String.class);


		// JSON 문자열을 ObjectMapper를 사용하여 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(stringResponseEntity.getBody());

		// access_token 추출
		JsonNode kakaoAccount = responseJson.get("kakao_account");
		String email = kakaoAccount.get("email").asText();

		User existUser = userRepository.findUserByEmail(email).orElseThrow(()->new CustomException(ErrorCode.FORBIDDEN_USER));

		if(existUser==null) {
			User user = User.builder()
				.email(email)
				.build();

			existUser = userRepository.save(user);

		}

		LoginResponseDto loginResponseDto = LoginResponseDto.builder()
			.userSeq(existUser.getSeq())
			.build();

		return loginResponseDto;

	}
}
