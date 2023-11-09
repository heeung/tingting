package com.alsif.book.concert.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ConcertSeatGradeInfoBaseDto {

	private Long concertSeatInfoSeq;
	private Boolean book;
	private Integer price;
}
