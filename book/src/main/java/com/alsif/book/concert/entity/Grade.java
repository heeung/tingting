package com.alsif.book.concert.entity;

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
@Table(name = "grade")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_seq", nullable = false)
	private Concert concert;

	@Column(nullable = false, length = 10)
	private String name;

	@Column(nullable = false)
	private Integer price;

	@Builder
	public Grade(Concert concert, String name, Integer price) {
		this.concert = concert;
		this.name = name;
		this.price = price;
	}

	public void setConcert(Concert concert) {
		if (this.concert != null) {
			this.concert.getGrades().remove(this);
		}

		this.concert = concert;
		concert.getGrades().add(this);
	}

	@Override
	public String toString() {
		return "Grade {"
			+ "seq=" + seq
			+ ", name='" + name + '\''
			+ ", price=" + price
			+ '}';
	}
}
