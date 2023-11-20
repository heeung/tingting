package com.alsif.tingting.concert.dto.concerthall;

import java.util.List;

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
@Schema(description = "좌석 예매 요청")
public class ConcertSeatBookRequestDto {

	@Schema(description = "선택 좌석 PK 목록", type = "List<Integer>", example = "[154002,154003,154009]")
	private List<Long> seatSeqs;
}
