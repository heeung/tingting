package com.alsif.tingting.concert.entity.performer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alsif.tingting.concert.entity.Concert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_performer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertPerformer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performer_seq", nullable = false)
	private Performer performer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_seq", nullable = false)
	private Concert concert;

	@Builder
	public ConcertPerformer(Performer performer, Concert concert) {
		this.performer = performer;
		this.concert = concert;
	}

	public void setConcert(Concert concert) {
		if (this.concert != null) {
			this.concert.getConcertPerformers().remove(this);
		}

		this.concert = concert;
		concert.getConcertPerformers().add(this);
	}

	@Override
	public String toString() {
		return "ConcertPerformer {"
			+ "seq=" + seq
			+ ", performer=" + performer
			+ '}';
	}
}
