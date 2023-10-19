package com.alsif.tingting.global.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.repository.ConcertDetailRepository;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.concert.repository.ConcertSeatInfoRepository;
import com.alsif.tingting.concert.repository.GradeRepository;
import com.alsif.tingting.concert.repository.concerthall.ConcertHallRepository;
import com.alsif.tingting.concert.repository.concerthall.ConcertHallSeatRepository;
import com.alsif.tingting.concert.repository.performer.ConcertPerformerRepository;
import com.alsif.tingting.concert.repository.performer.PerformerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DummyService {

	private final TicketRepository ticketRepository;
	private final TicketSeatRepository ticketSeatRepository;
	private final ConcertHallRepository concertHallRepository;
	private final ConcertHallSeatRepository concertHallSeatRepository;
	private final ConcertPerformerRepository concertPerformerRepository;
	private final PerformerRepository performerRepository;
	private final ConcertDetailRepository concertDetailRepository;
	private final ConcertRepository concertRepository;
	private final ConcertSeatInfoRepository concertSeatInfoRepository;
	private final GradeRepository gradeRepository;

	public void insertAllData() {
		return;
	}

	// 가수 넣기
	@Transactional
	public void insertPerformers() {
	}

	// 콘서트홀, 콘서트홀 좌석 넣기
	public void insertConcertHalls() {

	}

	// 회원정보, 포인트(회원가입 때 주는거) 정보 넣기
	public void insertUsers() {

	}

	// 콘서트정보, 상세, 콘서트출연자
	public void insertConcerts() {

	}

	// 콘서트 좌석 정보 넣기
	public void insertConcertSeatInfos() {

	}

	// 예매 티켓 정보 넣기
	public void insertTickets() {

	}
}
