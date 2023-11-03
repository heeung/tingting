package com.alsif.tingting.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
import com.alsif.tingting.user.dto.TicketListResponseDto;
import com.alsif.tingting.user.repository.UserRepository;

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
}
