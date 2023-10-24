package com.alsif.tingting.concert.dto;

import com.alsif.tingting.global.dto.PageableDto;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Schema(description = "콘서트 목록 요청")
public class ConcertListRequestDto extends PageableDto {

	@Schema(description = "예매중: now, 예매임박: soon", type = "String", example = "now")
	private String orderBy;

	@Schema(description = "콘서트 시작일", type = "String", example = "2023-10-19")
	private String startDate;

	@Schema(description = "콘서트 종료일", type = "String", example = "2023-10-24")
	private String endDate;

	@Schema(description = "콘서트 장소(시/도)", type = "String", example = "서울")
	private String place;

	@Schema(description = "검색 키워드", type = "String", example = "임영웅")
	private String searchWord;

	@Builder
	public ConcertListRequestDto(Integer currentPage, Integer itemCount, String orderBy, String startDate,
		String endDate,
		String place, String searchWord) {
		super(currentPage, itemCount);
		this.orderBy = orderBy;
		this.startDate = startDate;
		this.endDate = endDate;
		this.place = place;
		this.searchWord = searchWord;
	}
}
