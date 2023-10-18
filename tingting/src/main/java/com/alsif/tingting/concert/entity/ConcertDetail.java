package com.alsif.tingting.concert.entity;

import java.time.LocalDateTime;

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
@Table(name = "concert_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_seq", nullable = false)
	private Concert concert;

	@Column(nullable = false)
	private LocalDateTime holdDate;

	@Column(nullable = false)
	private LocalDateTime bookOpenDate;

	@Column(nullable = false)
	private LocalDateTime bookCloseDate;

	@Builder
	public ConcertDetail(Concert concert, LocalDateTime holdDate, LocalDateTime bookOpenDate,
		LocalDateTime bookCloseDate) {
		this.concert = concert;
		this.holdDate = holdDate;
		this.bookOpenDate = bookOpenDate;
		this.bookCloseDate = bookCloseDate;
	}

	@Override
	public String toString() {
		return "ConcertDetail {"
			+ "seq=" + seq
			+ ", concert=" + concert
			+ ", holdDate=" + holdDate
			+ ", bookOpenDate=" + bookOpenDate
			+ ", bookCloseDate=" + bookCloseDate
			+ '}';
	}
}
