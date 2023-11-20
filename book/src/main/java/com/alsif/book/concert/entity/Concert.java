package com.alsif.book.concert.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alsif.book.concert.entity.concerthall.ConcertHall;
import com.alsif.book.concert.entity.performer.ConcertPerformer;

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
	private Integer seq;

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

	@Column(nullable = false)
	private LocalDateTime bookOpenDate;

	@Column(nullable = false)
	private LocalDateTime bookCloseDate;

	@OneToMany(mappedBy = "concert")
	private final List<ConcertDetail> concertDetails = new ArrayList<>();

	@OneToMany(mappedBy = "concert")
	private final List<ConcertPerformer> concertPerformers = new ArrayList<>();

	@OneToMany(mappedBy = "concert")
	private final List<Grade> grades = new ArrayList<>();

	@Builder
	public Concert(ConcertHall concertHall, String name, String imageUrl, String info, LocalDateTime holdOpenDate,
		LocalDateTime holdCloseDate, LocalDateTime bookOpenDate, LocalDateTime bookCloseDate) {
		this.concertHall = concertHall;
		this.name = name;
		this.imageUrl = imageUrl;
		this.info = info;
		this.holdOpenDate = holdOpenDate;
		this.holdCloseDate = holdCloseDate;
		this.bookOpenDate = bookOpenDate;
		this.bookCloseDate = bookCloseDate;
	}

	public static Concert constructBySeq(Integer seq) {
		return new Concert(seq);
	}

	private Concert(Integer seq) {
		this.seq = seq;
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
