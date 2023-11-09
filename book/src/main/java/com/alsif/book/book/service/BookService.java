package com.alsif.book.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alsif.book.book.entity.Ticket;
import com.alsif.book.book.repository.TicketRepository;
import com.alsif.book.concert.dto.ConcertSeatGradeInfoBaseDto;
import com.alsif.book.concert.dto.concerthall.ConcertSeatBookRequestDto;
import com.alsif.book.concert.dto.concerthall.SuccessResponseDto;
import com.alsif.book.concert.entity.ConcertDetail;
import com.alsif.book.concert.repository.ConcertSeatInfoRepository;
import com.alsif.book.global.constant.ErrorCode;
import com.alsif.book.global.exception.CustomException;
import com.alsif.book.global.service.RedisService;
import com.alsif.book.global.repository.JDBCRepository;
import com.alsif.book.user.entity.Point;
import com.alsif.book.user.entity.User;
import com.alsif.book.user.repository.PointRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

	private final ConcertSeatInfoRepository concertSeatInfoRepository;
	private final TicketRepository ticketRepository;
	private final PointRepository pointRepository;
	private final RedisService redisService;
	private final JDBCRepository jdbcRepository;

	static final String CONCERT_SEAT_INFO_KEY = "concert_seat_info_";

	/*
		선택 좌석 예매 요청
	 */
	@Transactional
	public SuccessResponseDto book(Integer userSeq, Integer concertDetailSeq,
		ConcertSeatBookRequestDto requestDto) {

		User user = User.seqOf(userSeq);
		ConcertDetail concertDetail = ConcertDetail.seqOf(concertDetailSeq);
		List<Long> seatSeqs = requestDto.getSeatSeqs();

		List<ConcertSeatGradeInfoBaseDto> concertSeatGradeInfos = checkSeatBooked(seatSeqs);

		concertSeatInfoRepository.updateBooks(seatSeqs, true);

		int totalPrice = concertSeatGradeInfos.stream()
			.mapToInt(ConcertSeatGradeInfoBaseDto::getPrice)
			.sum();

		Ticket ticket = ticketRepository.save(Ticket.builder()
			.user(user)
			.concertDetail(concertDetail)
			.build());

		jdbcRepository.saveTicketSeats(seatSeqs, ticket);

		Point point = pointRepository.findTop1ByUser_SeqOrderBySeqDesc(userSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_DATA_FOUND));

		int currentMoney = point.getTotal();
		if (currentMoney < totalPrice) {
			throw new CustomException(ErrorCode.LACK_POINT);
		}

		pointRepository.save(Point.builder()
			.user(user)
			.ticket(ticket)
			.pay(totalPrice * -1)
			.total(currentMoney - totalPrice)
			.build());

		// redis 좌석 정보 저장
		seatSeqs.forEach(seatSeq -> redisService.setValue(CONCERT_SEAT_INFO_KEY + seatSeq, "1"));

		return SuccessResponseDto.builder().message("true").build();
	}

	/*
		좌석 사용 가능 여부 유효성 검사
	 */
	private List<ConcertSeatGradeInfoBaseDto> checkSeatBooked(List<Long> seatSeqs) {
		seatSeqs.forEach(seatSeq -> {
			String seatAvailability = redisService.getValue(CONCERT_SEAT_INFO_KEY + seatSeq);
			if ("1".equals(seatAvailability)) {
				throw new CustomException(ErrorCode.NOT_AVAILABLE_SEAT);
			}
		});

		List<ConcertSeatGradeInfoBaseDto> concertSeatGradeInfos = concertSeatInfoRepository.findByConcertSeatInfoJoinGrade(
			seatSeqs);

		if (concertSeatGradeInfos.stream().anyMatch(ConcertSeatGradeInfoBaseDto::getBook)) {
			throw new CustomException(ErrorCode.NOT_AVAILABLE_SEAT);
		}
		return concertSeatGradeInfos;
	}

}
