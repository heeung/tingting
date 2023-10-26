package com.alsif.tingting.concert.dto.concerthall;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Schema(description = "좌석 예약 상태")
public class SeatBookBaseDto {

	@Schema(description = "좌석 예매 상태", type = "Boolean", example = "예매 가능: false, 예매 불가능: true")
	private Boolean book;
}
