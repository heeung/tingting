package com.alsif.tingting.concert.entity.concerthall;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_hall_seat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertHallSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_hall_seq", nullable = false)
	private ConcertHall concertHall;

	@Column(nullable = false, length = 10)
	private String section;

	@Column(nullable = false, length = 10)
	private String seat;

	@Builder
	public ConcertHallSeat(ConcertHall concertHall, String section, String seat) {
		this.concertHall = concertHall;
		this.section = section;
		this.seat = seat;
	}

	public void setConcertHall(ConcertHall concertHall) {
		if (this.concertHall != null) {
			this.concertHall.getConcertHallSeats().remove(this);
		}

		this.concertHall = concertHall;
		concertHall.getConcertHallSeats().add(this);
	}

	@Override
	public String toString() {
		return "ConcertHallSeat {"
			+ "seq=" + seq
			+ ", concertHall=" + concertHall
			+ ", section='" + section + '\''
			+ ", seat='" + seat + '\''
			+ '}';
	}
}
