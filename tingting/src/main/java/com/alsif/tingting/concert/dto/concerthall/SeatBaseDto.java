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
@Schema(description = "좌석 정보")
public class SeatBaseDto {

	@Schema(description = "예매 PK", type = "Long", example = "1768")
	private Long ticketSeq;

	@Schema(description = "좌석 코드 대분류", type = "String", example = "A")
	private String section;

	@Schema(description = "좌석 코드 소분류", type = "String", example = "D1")
	private String seat;

	@Schema(description = "좌석 가격", type = "Long", example = "77000")
	private Long price;

	@Schema(description = "좌석 등급", type = "String", example = "VIP")
	private String name;
}
