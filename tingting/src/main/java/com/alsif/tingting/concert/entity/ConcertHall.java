package com.alsif.tingting.concert.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_hall")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertHall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 10)
	private String city;

	@Column(nullable = false, length = 1)
	private String pattern;

	@Builder
	public ConcertHall(String name, String city, String pattern) {
		this.name = name;
		this.city = city;
		this.pattern = pattern;
	}

	@Override
	public String toString() {
		return "ConcertHall {"
			+ "seq=" + seq
			+ ", name='" + name + '\''
			+ ", city='" + city + '\''
			+ ", pattern='" + pattern + '\''
			+ '}';
	}
}
