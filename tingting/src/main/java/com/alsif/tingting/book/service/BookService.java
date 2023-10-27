package com.alsif.tingting.book.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alsif.tingting.book.entity.Ticket;
import com.alsif.tingting.book.entity.TicketSeat;
import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.dto.concerthall.ConcertHallPatternResponseDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSeatBookRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoResponseDto;
import com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto;
import com.alsif.tingting.concert.dto.concerthall.SuccessResponseDto;
import com.alsif.tingting.concert.entity.Concert;
import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;
import com.alsif.tingting.concert.entity.Grade;
import com.alsif.tingting.concert.repository.ConcertDetailRepository;
import com.alsif.tingting.concert.repository.ConcertHallSeatRepository;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.concert.repository.ConcertSeatInfoRepository;
import com.alsif.tingting.concert.repository.GradeRepository;
import com.alsif.tingting.global.constant.ErrorCode;
import com.alsif.tingting.global.exception.CustomException;
import com.alsif.tingting.user.entity.Point;
import com.alsif.tingting.user.entity.User;
import com.alsif.tingting.user.repository.PointRepository;
import com.alsif.tingting.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

	private final ConcertRepository concertRepository;
	private final ConcertHallSeatRepository concertHallSeatRepository;
	private final ConcertSeatInfoRepository concertSeatInfoRepository;
	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;
	private final ConcertDetailRepository concertDetailRepository;
	private final TicketSeatRepository ticketSeatRepository;
	private final GradeRepository gradeRepository;
	private final PointRepository pointRepository;

	/*
		콘서트장 정보 조회
	 */
	public ConcertHallPatternResponseDto findConcertPattern(Long concertSeq) {
		Optional<Concert> concert = concertRepository.findById(concertSeq);
		if (concert.isEmpty()) {
			throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_SEQ);
		}

		log.info(concert.toString());
		return ConcertHallPatternResponseDto.builder()
			.concertHallSeq(concert.get().getConcertHall().getSeq())
			.pattern(concert.get().getConcertHall().getPattern())
			.build();
	}

	/*
		콘서트장 섹션별 좌석 정보 조회
	 */
	public List<ConcertSectionSeatInfoResponseDto> findConcertSectionSeatInfoList(Long concertDetailSeq,
		ConcertSectionSeatInfoRequestDto requestDto) {

		List<ConcertSectionSeatInfoResponseDto> concertSectionSeatInfoResponseDtos
			= concertHallSeatRepository.findAllSectionSeatInfo(concertDetailSeq, requestDto.getConcertHallSeq(),
			requestDto.getTarget());

		if (concertSectionSeatInfoResponseDtos.size() == 0) {
			throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_SECTION_INFO);
		}

		log.info("{} 섹션의 조회된 좌석 개수: {}", requestDto.getTarget(), concertSectionSeatInfoResponseDtos.size());
		return concertSectionSeatInfoResponseDtos;
	}

	/*
		선택 좌석의 예매 가능 여부 확인
	 */
	public SuccessResponseDto isSeatAvailable(Long concertDetailSeq,
		ConcertSeatBookRequestDto requestDto) {

		for (Long seatSeq : requestDto.getSeatSeqs()) {
			Optional<SeatBookBaseDto> seatBookBaseDto
				= concertSeatInfoRepository.findBookByConcertDetail_SeqAAndConcertHallSeat_Seq(
				concertDetailSeq, seatSeq);
			if (seatBookBaseDto.isEmpty()) {
				throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_HALL_SEAT_SEQ);
			}
			if (seatBookBaseDto.get().getBook()) {
				throw new CustomException(ErrorCode.NOT_AVAILABLE_SEAT);
			}
		}

		return SuccessResponseDto.builder().message("true").build();
	}

	/*
		선택 좌석 예매 요청
	 */
	@Transactional
	public SuccessResponseDto book(Long userSeq, Long concertDetailSeq,
		ConcertSeatBookRequestDto requestDto) {

		Optional<User> user = userRepository.findById(userSeq);
		if (user.isEmpty()) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}

		Optional<ConcertDetail> concertDetail = concertDetailRepository.findById(concertDetailSeq);
		if (concertDetail.isEmpty()) {
			throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_DETAIL_SEQ);
		}

		// 예매된 좌석으로 변경
		List<ConcertSeatInfo> concertSeatInfos = new ArrayList<>();
		long totalPrice = 0;
		for (Long seatSeq : requestDto.getSeatSeqs()) {
			Optional<ConcertSeatInfo> concertSeatInfo
				= concertSeatInfoRepository.findByConcertDetail_SeqAndConcertHallSeat_Seq(
				concertDetailSeq, seatSeq);
			if (concertSeatInfo.isEmpty()) {
				throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_HALL_SEAT_SEQ);
			}
			if (concertSeatInfo.get().getBook()) {
				throw new CustomException(ErrorCode.NOT_AVAILABLE_SEAT);
			}
			// 예매 상태 변경
			concertSeatInfo.get().updateBook();
			// 좌석 가격 조회
			Optional<Grade> grade = gradeRepository.findById(concertSeatInfo.get().getGrade().getSeq());
			if (grade.isEmpty()) {
				throw new CustomException(ErrorCode.NO_DATA_FOUND);
			}
			totalPrice += grade.get().getPrice();
			concertSeatInfos.add(concertSeatInfo.get());
		}

		// 예매 티켓 발행
		Ticket ticket = ticketRepository.save(Ticket.builder()
			.user(user.get())
			.concertDetail(concertDetail.get())
			.build());

		for (ConcertSeatInfo concertSeatInfo : concertSeatInfos) {
			ticketSeatRepository.save(TicketSeat.builder()
				.ticket(ticket)
				.concertSeatInfo(concertSeatInfo)
				.build());
		}

		// 포인트 차감
		Optional<Point> point = pointRepository.findTop1ByUser_SeqOrderBySeqDesc(userSeq);
		if (point.isEmpty()) {
			throw new CustomException(ErrorCode.NO_DATA_FOUND);
		}

		long currentMoney = point.get().getTotal();
		if (currentMoney < totalPrice) {
			throw new CustomException(ErrorCode.LACK_POINT);
		}

		pointRepository.save(Point.builder()
			.user(user.get())
			.ticket(ticket)
			.pay(totalPrice * -1)
			.total(currentMoney - totalPrice)
			.build());

		return SuccessResponseDto.builder().message("true").build();
	}

	/*
		예매 취소
	 */
	@Transactional
	public SuccessResponseDto reservationCancellation(Long userSeq, Long ticketSeq) {

		Optional<User> user = userRepository.findById(userSeq);
		if (user.isEmpty()) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}

		// 예매 취소일 추가
		Optional<Ticket> ticket = ticketRepository.findById(ticketSeq);
		if (ticket.isEmpty()) {
			throw new CustomException(ErrorCode.BAD_REQUEST_TICKET_SEQ);
		}
		// 티켓 구매자 학인
		if (!ticket.get().getUser().getSeq().equals(userSeq)) {
			throw new CustomException(ErrorCode.FORBIDDEN_USER);
		}
		ticket.get().updateDeletedDate(LocalDateTime.now());

		// 예매 가능 여부 업데이트
		List<ConcertSeatInfo> concertSeatInfos = ticketSeatRepository.findAllByTicketSeq(ticketSeq);
		if (concertSeatInfos.size() == 0) {
			throw new CustomException(ErrorCode.NO_DATA_FOUND);
		}

		long totalPrice = 0;
		for (ConcertSeatInfo concertSeatInfo : concertSeatInfos) {
			concertSeatInfo.updateBook();
			Optional<Grade> grade = gradeRepository.findById(concertSeatInfo.getGrade().getSeq());
			if (grade.isEmpty()) {
				throw new CustomException(ErrorCode.NO_DATA_FOUND);
			}
			totalPrice += grade.get().getPrice();
		}

		// 포인트 복구
		Optional<Point> point = pointRepository.findTop1ByUser_SeqOrderBySeqDesc(userSeq);
		if (point.isEmpty()) {
			throw new CustomException(ErrorCode.NO_DATA_FOUND);
		}

		long currentMoney = point.get().getTotal();
		pointRepository.save(Point.builder()
			.user(user.get())
			.ticket(ticket.get())
			.pay(totalPrice)
			.total(currentMoney + totalPrice)
			.build());

		return SuccessResponseDto.builder().message("true").build();
	}
}
