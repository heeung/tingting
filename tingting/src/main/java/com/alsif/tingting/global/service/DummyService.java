package com.alsif.tingting.global.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.entity.concerthall.ConcertHall;
import com.alsif.tingting.concert.entity.concerthall.ConcertHallSeat;
import com.alsif.tingting.concert.entity.performer.Performer;
import com.alsif.tingting.concert.repository.ConcertDetailRepository;
import com.alsif.tingting.concert.repository.ConcertRepository;
import com.alsif.tingting.concert.repository.ConcertSeatInfoRepository;
import com.alsif.tingting.concert.repository.GradeRepository;
import com.alsif.tingting.concert.repository.concerthall.ConcertHallRepository;
import com.alsif.tingting.concert.repository.concerthall.ConcertHallSeatRepository;
import com.alsif.tingting.concert.repository.performer.ConcertPerformerRepository;
import com.alsif.tingting.concert.repository.performer.PerformerRepository;
import com.alsif.tingting.user.repository.PointRepository;
import com.alsif.tingting.user.repository.UserConcertRepository;
import com.alsif.tingting.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DummyService {

	private final TicketRepository ticketRepository;
	private final TicketSeatRepository ticketSeatRepository;
	private final ConcertHallRepository concertHallRepository;
	private final ConcertHallSeatRepository concertHallSeatRepository;
	private final ConcertPerformerRepository concertPerformerRepository;
	private final PerformerRepository performerRepository;
	private final ConcertDetailRepository concertDetailRepository;
	private final ConcertRepository concertRepository;
	private final ConcertSeatInfoRepository concertSeatInfoRepository;
	private final GradeRepository gradeRepository;
	private final PointRepository pointRepository;
	private final UserConcertRepository userConcertRepository;
	private final UserRepository userRepository;
	private DummyList dummyList;

	/**
	 * 1. 가수를 넣는다. (이 행위는 데이터 넣는 첫날에만 발생함
	 * 2. 콘서트홀, 콘서트 좌석을 넣는다. 근데 콘서트홀 좌석 패턴에 대해서 아직 얘기를 안해봤는데? 흠......
	 * -> 이걸 할 때 연관관계를 걸어줘야하기 때문에, Entity하나 만들고, 그에 종속되는 Entity를 만든다음 매핑 해서 인서트해야됨
	 * 3. 회원정보, 포인트 정보 넣기
	 * 4. 콘서트 정보, 상세, 콘서트 출연자 넣기
	 * 5. 콘서트 좌석 정보 넣기
	 * 6. 예매 티켓 정보 넣기
	 *
	 * 근데 여기서 중요한건 현재 메서드별로 리턴값 void로 해놨는데, 특정 메서드는 분리 및 합체 되면서
	 * 리턴값을 엔티티를 받아다가 그걸로 추가적으로 매핑하거나 하는 연산이 필요할 수도 있음.
	 * 이상
	 */

	@Transactional
	public void insertAllData() {
		dummyList = new DummyList();
		insertPerformers();
		return;
	}

	// 가수 넣기
	public void insertPerformers() {
		List<String> singer = dummyList.getPerformers();
		List<String> performersImage = dummyList.getPerformersImage();
		List<Performer> performers = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			Performer performer = Performer.builder()
				.name(singer.get(i))
				.imageUrl(performersImage.get(i))
				.build();
			performers.add(performer);
		}

		performerRepository.saveAll(performers);

	}

	// 콘서트홀, 콘서트홀 좌석 넣기
	public void insertConcertHalls() {
		List<String> concertHallNames = dummyList.getConcertHallName();
		List<String> concertHallCities = dummyList.getConcertHallCity();

		List<ConcertHall> concertHalls = new ArrayList<>();
		List<ConcertHallSeat> concertHallSeats = new ArrayList<>();

		makeConcertHalls(concertHallNames, concertHallCities, concertHalls);

		makeConcertHallSeats(concertHalls, concertHallSeats);

		concertHallRepository.saveAll(concertHalls);
		concertHallSeatRepository.saveAll(concertHallSeats);
	}

	// 회원정보, 포인트(회원가입 때 주는거) 정보 넣기
	public void insertUsers() {

	}

	// 콘서트정보, 상세, 콘서트출연자
	public void insertConcerts() {

	}

	// 콘서트 좌석 정보 넣기
	public void insertConcertSeatInfos() {

	}

	// 예매 티켓 정보 넣기
	public void insertTickets() {

	}


	private static void makeConcertHallSeats(List<ConcertHall> concertHalls, List<ConcertHallSeat> concertHallSeats) {
		for (ConcertHall concertHall : concertHalls) {
			for (char section = 'A'; section <= 'J'; section++) {
				for (char seatAlphabet = 'A'; seatAlphabet <= 'J'; seatAlphabet++) {
					for (int seatNumber = 1; seatNumber <= 20; seatNumber++) {
						ConcertHallSeat concertHallSeat = ConcertHallSeat.builder()
							.section(String.valueOf(section))
							.seat(String.valueOf(seatAlphabet) + seatNumber)
							.build();
						concertHallSeat.setConcertHall(concertHall);

						concertHallSeats.add(concertHallSeat);

					}
				}
			}
		}
	}

	private static void makeConcertHalls(List<String> concertHallNames, List<String> concertHallCities,
		List<ConcertHall> concertHalls) {
		for (String concertHallName : concertHallNames) {
			for (String concertHallCity : concertHallCities) {
				ConcertHall concertHall = ConcertHall.builder()
					.name(concertHallName)
					.city(concertHallCity)
					.pattern("A")
					.build();
				concertHalls.add(concertHall);
			}
		}
	}

}
