package com.alsif.tingting.book.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.alsif.tingting.concert.dto.concerthall.SuccessResponseDto;
import com.alsif.tingting.concert.entity.Concert;
import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;
import com.alsif.tingting.concert.entity.concerthall.ConcertHall;
import com.alsif.tingting.concert.repository.ConcertDetailRepository;
import com.alsif.tingting.concert.repository.ConcertHallSeatRepository;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.concert.repository.ConcertSeatInfoRepository;
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
	private final PointRepository pointRepository;

	/*
		콘서트장 정보 조회
	 */
	public ConcertHallPatternResponseDto findConcertPattern(Long concertSeq) {
		Concert concert = concertRepository.findById(concertSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_CONCERT_SEQ));
		log.info(concert.toString());

		ConcertHall concertHall = concert.getConcertHall();
		if (concertHall == null) {
			throw new CustomException(ErrorCode.NO_DATA_FOUND);
		}

		return ConcertHallPatternResponseDto.builder()
			.concertHallSeq(concertHall.getSeq())
			.pattern(concertHall.getPattern())
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
	public SuccessResponseDto isSeatAvailable(Long concertDetailSeq, ConcertSeatBookRequestDto requestDto) {
		// 좌석별 예매 가능 여부 확인
		for (Long seatSeq : requestDto.getSeatSeqs()) {
			this.checkSeatAvailability(concertDetailSeq, seatSeq);
		}

		return SuccessResponseDto.builder().message("true").build();
	}

	/*
		선택 좌석 예매 요청
	 */
	@Transactional
	public SuccessResponseDto book(Long userSeq, Long concertDetailSeq,
		ConcertSeatBookRequestDto requestDto) {

		User user = userRepository.findById(userSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));

		ConcertDetail concertDetail = concertDetailRepository.findById(concertDetailSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_CONCERT_DETAIL_SEQ));

		List<ConcertSeatInfo> concertSeatInfos = new ArrayList<>();
		long totalPrice = 0;

		// 좌석별 예매 상태 변경
		for (Long seatSeq : requestDto.getSeatSeqs()) {
			// 예매 되지 않은 좌석이라면, 예매 처리
			ConcertSeatInfo concertSeatInfo = this.checkSeatAvailability(concertDetailSeq, seatSeq);
			concertSeatInfo.updateBook();

			// 좌석 가격 조회
			Long price = concertSeatInfo.getGrade().getPrice();
			if (price == null) {
				throw new CustomException(ErrorCode.NO_DATA_FOUND);
			}

			totalPrice += price;
			concertSeatInfos.add(concertSeatInfo);
		}

		// 예매 티켓 발행
		Ticket ticket = ticketRepository.save(Ticket.builder()
			.user(user)
			.concertDetail(concertDetail)
			.build());

		for (ConcertSeatInfo concertSeatInfo : concertSeatInfos) {
			ticketSeatRepository.save(TicketSeat.builder()
				.ticket(ticket)
				.concertSeatInfo(concertSeatInfo)
				.build());
		}

		// 포인트 차감
		Point point = pointRepository.findTop1ByUser_SeqOrderBySeqDesc(userSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_DATA_FOUND));

		long currentMoney = point.getTotal();
		if (currentMoney < totalPrice) {
			throw new CustomException(ErrorCode.LACK_POINT);
		}

		pointRepository.save(Point.builder()
			.user(user)
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

		User user = userRepository.findById(userSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));

		Ticket ticket = ticketRepository.findById(ticketSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_TICKET_SEQ));

		// 이미 예매 취소된 티켓일 경우
		if (ticket.getDeletedDate() != null) {
			throw new CustomException(ErrorCode.ALREADY_CANCELED_TICKET);
		}

		// 티켓 구매자 확인, 후 예매 취소 처리
		if (!ticket.getUser().getSeq().equals(userSeq)) {
			throw new CustomException(ErrorCode.FORBIDDEN_USER);
		}
		ticket.updateDeletedDate(LocalDateTime.now());

		// 취소한 좌석 예매 가능 처리
		List<ConcertSeatInfo> concertSeatInfos = ticketSeatRepository.findAllByTicketSeq(ticketSeq);
		if (concertSeatInfos.size() == 0) {
			throw new CustomException(ErrorCode.NO_DATA_FOUND);
		}

		long totalPrice = 0;
		// 좌석 정보 업데이트, 환불 가격 확인
		for (ConcertSeatInfo concertSeatInfo : concertSeatInfos) {
			concertSeatInfo.updateBook();

			Long price = concertSeatInfo.getGrade().getPrice();
			if (price == null) {
				throw new CustomException(ErrorCode.NO_DATA_FOUND);
			}

			totalPrice += price;
		}

		// 포인트 환불
		Point point = pointRepository.findTop1ByUser_SeqOrderBySeqDesc(userSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.NO_DATA_FOUND));

		long currentMoney = point.getTotal();

		log.info("현재 포인트: {}, 환불 받을 포인트: {}", currentMoney, totalPrice);

		pointRepository.save(Point.builder()
			.user(user)
			.ticket(ticket)
			.pay(totalPrice)
			.total(currentMoney + totalPrice)
			.build());

		return SuccessResponseDto.builder().message("true").build();
	}

	/*
		좌석 사용 가능 여부 유효성 검사
	 */
	private ConcertSeatInfo checkSeatAvailability(Long concertDetailSeq, Long concertSeatInfoSeq) {
		ConcertSeatInfo concertSeatInfo = concertSeatInfoRepository.findById(concertSeatInfoSeq)
			.orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_CONCERT_HALL_SEAT_SEQ));

		if (!concertSeatInfo.getConcertDetail().getSeq().equals(concertDetailSeq)) {
			throw new CustomException(ErrorCode.BAD_REQUEST_CONCERT_DETAIL_SEQ);
		}

		if (concertSeatInfo.getBook()) {
			throw new CustomException(ErrorCode.NOT_AVAILABLE_SEAT);
		}

		return concertSeatInfo;
	}
}
