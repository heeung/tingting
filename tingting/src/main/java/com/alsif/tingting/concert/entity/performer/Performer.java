package com.alsif.tingting.concert.entity.performer;

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
@Table(name = "performer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false, length = 40)
	private String name;

	private String imageUrl;

	@Builder
	public Performer(String name, String imageUrl) {
		this.name = name;
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Performer {"
			+ "seq=" + seq
			+ ", name='" + name + '\''
			+ ", imageUrl='" + imageUrl + '\''
			+ '}';
	}
}
