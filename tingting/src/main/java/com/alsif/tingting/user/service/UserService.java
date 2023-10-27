package com.alsif.tingting.user.service;

import java.util.List;

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
import com.alsif.tingting.global.dto.PageableDto;
import com.alsif.tingting.user.dto.TicketListResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final ConcertRepository concertRepository;
	private final TicketRepository ticketRepository;
	private final TicketSeatRepository ticketSeatRepository;

	/*
		찜 목록 가져오기
	 */
	public ConcertListResponseDto findFavoriteList(Long userSeq, PageableDto requestDto) {
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
	public TicketListResponseDto findTicketList(Long userSeq, PageableDto requestDto) {
		// 1. 회원이 가지고 있는 모든 예매 내역 조회
		Pageable pageable = PageRequest.of(requestDto.getCurrentPage() - 1, requestDto.getItemCount());
		Page<TicketBaseDto> tickets = ticketRepository.findAllByUserSeq(userSeq, pageable);

		// 2. 해당 티켓의 구매 가격, 좌석 정보 저장
		for (TicketBaseDto ticket : tickets) {
			List<SeatBaseDto> seatBaseDtos = ticketSeatRepository.findAllPriceByTicketSeq(ticket.getTicketSeq());
			long totalPrice = 0;
			for (SeatBaseDto seatBaseDto : seatBaseDtos) {
				totalPrice += seatBaseDto.getPrice();
			}
			ticket.setTotalPrice(totalPrice);
			ticket.setSeats(seatBaseDtos);
		}
		log.info("예매한 콘서트 개수 (취소 내역 포함, 현재 페이지 개수): {}", tickets.getContent().size());

		return TicketListResponseDto.builder()
			.totalPage(tickets.getTotalPages())
			.currentPage(requestDto.getCurrentPage())
			.tickets(tickets.getContent())
			.build();
	}
}
