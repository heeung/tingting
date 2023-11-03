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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@Schema(description = "섹션(좌석 대분류)별 좌석 정보 요청")
public class ConcertSectionSeatInfoRequestDto {

	@Schema(description = "콘서트장 PK", type = "Long", example = "78")
	private Long concertHallSeq;

	@Schema(description = "섹션 이름", type = "String", example = "A")
	private String target;
}
