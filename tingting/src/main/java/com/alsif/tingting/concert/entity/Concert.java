package com.alsif.tingting.concert.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_hall_seq", nullable = false)
	private ConcertHall concertHall;

	@Column(nullable = false, length = 40)
	private String name;

	private String imageUrl;

	@Lob
	private String info;

	@Column(nullable = false)
	private LocalDateTime holdOpenDate;

	@Column(nullable = false)
	private LocalDateTime holdCloseDate;

	@Builder
	public Concert(ConcertHall concertHall, String name, String imageUrl, String info, LocalDateTime holdOpenDate,
		LocalDateTime holdCloseDate) {
		this.concertHall = concertHall;
		this.name = name;
		this.imageUrl = imageUrl;
		this.info = info;
		this.holdOpenDate = holdOpenDate;
		this.holdCloseDate = holdCloseDate;
	}

	@Override
	public String toString() {
		return "Concert {"
			+ "seq=" + seq
			+ ", concertHall=" + concertHall
			+ ", name='" + name + '\''
			+ ", imageUrl='" + imageUrl + '\''
			+ ", info='" + info + '\''
			+ ", holdOpenDate=" + holdOpenDate
			+ ", holdCloseDate=" + holdCloseDate
			+ '}';
	}
}
