package com.alsif.tingting.concert.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.alsif.tingting.concert.dto.performer.PerformerBaseDto;

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
@Schema(description = "콘서트 상세 정보 반환")
public class ConcertDetailResponseDto extends ConcertBaseDto {

	@Schema(description = "콘서트 설명", type = "String", example = "임영웅의 2023년 연말 콘서트, 그 첫번째 이야기를 서울에서 만나봅니다.")
	private String info; // Concert

	@Schema(description = "찜 여부", type = "Boolean", example = "true")
	private Boolean favorite; // UserConcert

	@Schema(description = "콘서트 예매 시작 일시", type = "String", example = "2023-10-20 14:00:00")
	private String bookOpenDate; // Concert

	@Schema(description = "콘서트 예매 종료 일시", type = "String", example = "2023-10-25 14:00:00")
	private String bookCloseDate; // Concert

	@Schema(description = "가수 정보", type = "List<PerformerBaseDto>")
	private List<PerformerBaseDto> performers; // Performer

	@Schema(description = "콘서트 공연 일시", type = "List<ConcertDetailBaseDto>")
	private List<ConcertDetailBaseDto> concertDetails; // ConcertDetail

	public ConcertDetailResponseDto(Long concertSeq, String name, LocalDateTime holdOpenDate,
		LocalDateTime holdCloseDate,
		String imageUrl, String concertHallName, String concertHallCity, String info, LocalDateTime bookOpenDate,
		LocalDateTime bookCloseDate) {
		super(concertSeq, name, holdOpenDate, holdCloseDate, imageUrl, concertHallName, concertHallCity);
		this.info = info;
		this.bookOpenDate = bookOpenDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.bookCloseDate = bookCloseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
