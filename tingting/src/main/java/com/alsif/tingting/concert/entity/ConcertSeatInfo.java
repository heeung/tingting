package com.alsif.tingting.concert.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.alsif.tingting.concert.entity.concerthall.ConcertHallSeat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_seat_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSeatInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_hall_seat_seq", nullable = false)
	private ConcertHallSeat concertHallSeat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_seq", nullable = false)
	private Grade grade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_detail_seq", nullable = false)
	private ConcertDetail concertDetail;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean book;

	@Builder
	public ConcertSeatInfo(ConcertHallSeat concertHallSeat, Grade grade, ConcertDetail concertDetail, Boolean book) {
		this.concertHallSeat = concertHallSeat;
		this.grade = grade;
		this.concertDetail = concertDetail;
		this.book = book;
	}

	public void updateBook() {
		this.book = !this.book;
	}

	@Override
	public String toString() {
		return "ConcertSeatInfo{"
			+ "seq=" + seq
			+ ", concertHallSeat=" + concertHallSeat
			+ ", grade=" + grade
			+ ", concertDetail=" + concertDetail
			+ ", book=" + book
			+ '}';
	}
}
