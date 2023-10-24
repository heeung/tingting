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
@Schema(description = "콘서트 찜 여부 반환")
public class ConcertFavoriteResponseDto {

	@Schema(description = "찜 여부", type = "Boolean", example = "true")
	private Boolean favorite;
}
