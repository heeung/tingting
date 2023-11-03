package com.alsif.tingting.concert.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "콘서트 정보")
public class ConcertBaseDto {

	@Schema(description = "콘서트 PK", example = "1")
	private Integer concertSeq;

	@Schema(description = "콘서트 이름", example = "임영웅 2023 겨울 콘서트 - 서울")
	private String name;

	@Schema(description = "콘서트 시작일", example = "2023-10-20 00:00:00")
	private String holdOpenDate;

	@Schema(description = "콘서트 종료일", example = "2023-10-21 00:00:00")
	private String holdCloseDate;

	@Schema(description = "콘서트 이미지", example = "https://newsimg.sedaily.com/2022/12/16/26EXB4JB5F_1.jpg")
	private String imageUrl;

	@Schema(description = "콘서트홀 이름", example = "삼성뮤직홀")
	private String concertHallName;

	@Schema(description = "콘서트홀 시도", example = "서울")
	private String concertHallCity;

	public ConcertBaseDto(Integer concertSeq, String name, LocalDateTime holdOpenDate, LocalDateTime holdCloseDate,
		String imageUrl,
		String concertHallName, String concertHallCity) {
		this.concertSeq = concertSeq;
		this.name = name;
		this.holdOpenDate = holdOpenDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.holdCloseDate = holdCloseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.imageUrl = imageUrl;
		this.concertHallName = concertHallName;
		this.concertHallCity = concertHallCity;
	}
}
