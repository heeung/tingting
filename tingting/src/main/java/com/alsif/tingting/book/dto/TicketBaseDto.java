package com.alsif.tingting.book.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.alsif.tingting.concert.dto.concerthall.SeatBaseDto;

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
@Schema(description = "예매 정보")
public class TicketBaseDto {

	@Schema(description = "예매 PK", type = "Long", example = "1")
	private Long ticketSeq; // Ticket

	@Schema(description = "예매 일시", type = "String", example = "2023-10-20 00:00:00")
	private String createdDate; // Ticket

	@Schema(description = "예매 취소 일시", type = "String", example = "2023-10-20 00:00:00")
	private String deletedDate; // Ticket

	@Schema(description = "총 예매 가격", type = "Long", example = "150000")
	private Long totalPrice;

	@Schema(description = "예매 좌석 정보", type = "List<SeatBaseDto>")
	private List<SeatBaseDto> seats; // concert_hall_seat

	@Schema(description = "콘서트 PK", type = "Long", example = "1")
	private Long concertSeq;

	@Schema(description = "콘서트 이름", type = "String", example = "임영웅 2023 겨울 콘서트 - 서울")
	private String name;

	@Schema(description = "콘서트 일시", type = "String", example = "2023-10-20 00:00:00")
	private String holdDate;

	@Schema(description = "콘서트 이미지", type = "String", example = "https://newsimg.sedaily.com/2022/12/16/26EXB4JB5F_1.jpg")
	private String imageUrl;

	@Schema(description = "콘서트홀 이름", type = "String", example = "삼성뮤직홀")
	private String concertHallName;

	@Schema(description = "콘서트홀 시도", type = "String", example = "서울")
	private String concertHallCity;

	public TicketBaseDto(Long ticketSeq, LocalDateTime createdDate, LocalDateTime deletedDate, Long pay,
		Long concertSeq, String name, LocalDateTime holdDate, String imageUrl, String concertHallName,
		String concertHallCity) {
		this.ticketSeq = ticketSeq;
		this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		if (deletedDate != null) {
			this.deletedDate = deletedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		this.totalPrice = pay * -1;
		this.concertSeq = concertSeq;
		this.name = name;
		this.holdDate = holdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.imageUrl = imageUrl;
		this.concertHallName = concertHallName;
		this.concertHallCity = concertHallCity;
	}
}
