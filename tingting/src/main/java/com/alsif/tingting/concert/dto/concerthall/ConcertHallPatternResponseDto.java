package com.alsif.tingting.concert.dto.concerthall;

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
@Schema(description = "콘서트장 정보")
public class ConcertHallPatternResponseDto {

	@Schema(description = "콘서트장 PK", type = "Long", example = "1")
	private Long concertHallSeq;

	@Schema(description = "콘서트장 패턴 정보", type = "String", example = "A")
	private String pattern;
}
