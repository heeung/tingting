package com.alsif.tingting.book.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alsif.tingting.concert.entity.ConcertSeatInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_seat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_seq", nullable = false)
	private Ticket ticket;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_seat_info_seq", nullable = false)
	private ConcertSeatInfo concertSeatInfo;

	@Builder
	public TicketSeat(Ticket ticket, ConcertSeatInfo concertSeatInfo) {
		this.ticket = ticket;
		this.concertSeatInfo = concertSeatInfo;
	}

	public void setTicket(Ticket ticket) {
		if (this.ticket != null) {
			this.ticket.getTicketSeats().remove(this);
		}

		this.ticket = ticket;
		ticket.getTicketSeats().add(this);
	}

	@Override
	public String toString() {
		return "TicketSeat {"
			+ "seq=" + seq
			+ ", ticket=" + ticket
			+ ", concertSeatInfo=" + concertSeatInfo
			+ '}';
	}
}
