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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@Schema(description = "콘서트 찜 요청")
public class ConcertFavoriteRequestDto {

	@Schema(description = "찜할 콘서트 PK", type = "Integer", example = "1")
	private Integer concertSeq;

	@Schema(description = "회원 PK", type = "Integer", example = "1")
	private Integer userSeq;
}
