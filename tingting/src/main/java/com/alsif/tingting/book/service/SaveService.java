package com.alsif.tingting.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alsif.tingting.book.entity.Ticket;
import com.alsif.tingting.book.entity.TicketSeat;
import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;
import com.alsif.tingting.user.entity.Point;
import com.alsif.tingting.user.entity.User;
import com.alsif.tingting.user.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveService {


	private final TicketRepository ticketRepository;
	private final TicketSeatRepository ticketSeatRepository;
	private final PointRepository pointRepository;

	@Transactional
	public void saveMethods(User user, ConcertDetail concertDetail, List<ConcertSeatInfo> concertSeatInfos,
		int totalPrice, int currentMoney) {
		concertSeatInfos.forEach(c -> c.updateBook(true));

		// 예매 티켓 발행
		Ticket ticket = ticketRepository.save(Ticket.builder()
			.user(user)
			.concertDetail(concertDetail)
			.build());

		for (ConcertSeatInfo concertSeatInfo : concertSeatInfos) {
			ticketSeatRepository.save(TicketSeat.builder()
				.ticket(ticket)
				.concertSeatInfo(concertSeatInfo)
				.build());
		}

		pointRepository.save(Point.builder()
			.user(user)
			.ticket(ticket)
			.pay(totalPrice * -1)
			.total(currentMoney - totalPrice)
			.build());
	}
}
