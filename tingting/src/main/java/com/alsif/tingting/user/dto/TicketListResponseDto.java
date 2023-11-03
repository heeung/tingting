package com.alsif.tingting.user.dto;

import java.util.List;

import com.alsif.tingting.book.dto.TicketBaseDto;

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
@Schema(description = "예매 내역 반환")
public class TicketListResponseDto {

	@Schema(description = "전체 페이지 개수", type = "Integer", example = "5")
	private Integer totalPage;

	@Schema(description = "현재 페이지 번호", type = "Integer", example = "1")
	private Integer currentPage;

	@Schema(description = "예매 정보", type = "List<TicketBaseDto>")
	private List<TicketBaseDto> tickets;
}
