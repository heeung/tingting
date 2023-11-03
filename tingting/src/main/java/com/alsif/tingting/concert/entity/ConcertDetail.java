package com.alsif.tingting.concert.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alsif.tingting.concert.dto.ConcertDetailBaseDto;

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

	@Builder
	public ConcertDetail(Concert concert, LocalDateTime holdDate) {
		this.concert = concert;
		this.holdDate = holdDate;
	}

	public static ConcertDetailBaseDto convertToConcertDetailBaseDto(ConcertDetail concertDetail) {
		return ConcertDetailBaseDto.builder()
			.seq(concertDetail.getSeq())
			.concertSeq(concertDetail.concert.getSeq())
			.holdDate(concertDetail.getHoldDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.build();
	}

	public void setConcert(Concert concert) {
		if (this.concert != null) {
			this.concert.getConcertDetails().remove(this);
		}

		this.concert = concert;
		concert.getConcertDetails().add(this);
	}

	@Override
	public String toString() {
		return "ConcertDetail {"
			+ "seq=" + seq
			+ ", holdDate=" + holdDate
			+ '}';
	}
}
