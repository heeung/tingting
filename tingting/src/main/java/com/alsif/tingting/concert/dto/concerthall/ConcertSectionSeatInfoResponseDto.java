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
@Schema(description = "섹션(좌석코드 대분류)별 좌석 정보")
public class ConcertSectionSeatInfoResponseDto {

	@Schema(description = "좌석 PK", type = "Long", example = "1")
	private Long concertSeatInfoSeq;

	@Schema(description = "좌석코드 대분류", type = "String", example = "A")
	private String section;

	@Schema(description = "좌석코드 소분류", type = "String", example = "A")
	private String seat;

	@Schema(description = "좌석 예매 상태", type = "Boolean", example = "예매 가능: false, 예매 불가능: true")
	private Boolean book;

	@Schema(description = "좌석 등급 이름", type = "String", example = "일반 / VIP")
	private String grade;
}
