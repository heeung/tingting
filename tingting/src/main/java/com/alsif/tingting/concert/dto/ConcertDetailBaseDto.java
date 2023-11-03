package com.alsif.tingting.concert.dto;

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
@Schema(description = "콘서트 상세 정보")
public class ConcertDetailBaseDto {

	@Schema(description = "콘서트 상세 PK", type = "Integer", example = "1")
	private Integer seq;

	@Schema(description = "콘서트 PK", type = "Integer", example = "1")
	private Integer concertSeq;

	@Schema(description = "콘서트 공연일", type = "String", example = "2023-10-26 14:00:00")
	private String holdDate;
}
