package com.alsif.tingting.global.dto;

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
@Schema(description = "페이지네이션 요청")
public class PageableDto {

	@Schema(description = "검색할 페이지", type = "Integer", example = "1")
	private Integer currentPage;

	@Schema(description = "페이지 당 콘서트 개수", type = "Integer", example = "18")
	private Integer itemCount;
}
