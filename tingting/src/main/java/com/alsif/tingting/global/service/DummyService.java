package com.alsif.tingting.global.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.entity.Concert;
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
import com.alsif.tingting.user.entity.Point;
import com.alsif.tingting.user.entity.User;
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
		insertConcertHalls();
		insertUsers();
		return;
	}

	// 가수 넣기
	public void insertPerformers() {
		List<String> singer = dummyList.getPerformers();
		List<String> performersImage = dummyList.getPerformersImage();

		List<Performer> performers = new ArrayList<>();

		makePerformers(singer, performersImage, performers);

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
		List<String> emails = dummyList.getEmails();

		List<User> users = new ArrayList<>();
		List<Point> points = new ArrayList<>();

		makeUsersAndPoints(emails, users, points);

		userRepository.saveAll(users);
		pointRepository.saveAll(points);
	}

	// 콘서트정보, 상세, 콘서트출연자
	public void insertConcerts() {
		List<String> concertNameHeaders = dummyList.getConcertNameHeaders();
		List<String> concertNameMiddles = dummyList.getConcertNameMiddles();
		List<String> concertNameTails = dummyList.getConcertNameTails();
		List<String> concertInfos = dummyList.getConcertInfo();
		List<String> concertImageUrls = dummyList.getConcertImageUrls();

		List<Concert> concerts = new ArrayList<>();

		for (int i = 0; i < 500; i++) {
			String concertName = makeConcertName(concertNameHeaders, concertNameMiddles, concertNameTails);
			String concertInfo = getRandomValue(concertInfos);
			String concertImageUrl = getRandomValue(concertImageUrls);

			int concertHoldPeriod = (int)(Math.random() * 5) + 1;

			LocalDateTime concertHoldOpenDate = makeConcertHoldOpenDate();
			LocalDateTime concertHoldCloseDate = makeConcertHoldCloseDate(concertHoldOpenDate, concertHoldPeriod);
			LocalDateTime concertBookOpenDate = makeConcertBookOpenDate(concertHoldOpenDate);
			LocalDateTime concertBookCloseDate = makeConcertBookCloseDate(concertHoldOpenDate);

			Concert.builder()
				.name(concertName)
				.info(concertInfo)
				.imageUrl(concertImageUrl)
				.holdOpenDate(concertHoldOpenDate)
				.holdCloseDate(concertHoldCloseDate)
				.build();
		}
	}

	// 콘서트 좌석 정보 넣기
	public void insertConcertSeatInfos() {

	}

	// 예매 티켓 정보 넣기
	public void insertTickets() {

	}

	private void makePerformers(List<String> singer, List<String> performersImage, List<Performer> performers) {
		for (int i = 0; i < 100; i++) {
			Performer performer = Performer.builder()
				.name(singer.get(i))
				.imageUrl(performersImage.get(i))
				.build();
			performers.add(performer);
		}
	}

	private void makeConcertHallSeats(List<ConcertHall> concertHalls, List<ConcertHallSeat> concertHallSeats) {
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

	private void makeConcertHalls(List<String> concertHallNames, List<String> concertHallCities,
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

	private LocalDateTime makeRandomDate() {
		int startDate = 1_597_581_600; // 2020년 8월 16일 21시 40분 00초
		int endDate = 1_647_581_600; // 2022년 3월 18일 14시 33분 20초

		int unixTime = (int)((endDate - startDate) * Math.random()) + startDate;

		return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Asia/Seoul"));
	}

	private static void makeUsersAndPoints(List<String> emails, List<User> users, List<Point> points) {
		for (String email : emails) {

			User user = User.builder()
				.email(email)
				.build();

			Point point = Point.builder()
				.pay(10000000L)
				.user(user)
				.total(10000000L)
				.build();

			users.add(user);
			points.add(point);
		}
	}

	private String makeConcertName(List<String> concertNameHeaders, List<String> concertNameMiddles,
		List<String> concertNameTails) {
		String nameHeader = getRandomValue(concertNameHeaders);
		String nameMiddle = getRandomValue(concertNameHeaders);
		String nameTail = getRandomValue(concertNameHeaders);
		return String.format("%s %s %s", nameHeader, nameMiddle, nameTail);
	}

	private String getRandomValue(List<String> concertNameHeaders) {
		String nameHeader = concertNameHeaders.get(getRandomValue(concertNameHeaders.size()));
		return nameHeader;
	}

	private int getRandomValue(int concertNameHeaderSize) {
		return (int)(Math.random() * concertNameHeaderSize);
	}

	private LocalDateTime makeConcertHoldOpenDate() {
		int year = (int)(Math.random() * 10) + 2014;
		int month = (int)(Math.random() * 12) + 1;
		int day = (int)(Math.random() * 30) + 1;
		if (month == 2 && day > 28) {
			day -= 2;
		}

		return LocalDateTime.of(year, month, day, 0, 0, 0);
	}

	private LocalDateTime makeConcertHoldCloseDate(LocalDateTime concertHoldOpenDate, int concertHoldingDate) {
		return concertHoldOpenDate.plusDays(concertHoldingDate);
	}

	private LocalDateTime makeConcertBookOpenDate(LocalDateTime concertHoldOpenDate) {
		return concertHoldOpenDate.minusDays(14).plusHours(19);
	}

	private LocalDateTime makeConcertBookCloseDate(LocalDateTime concertHoldOpenDate) {
		return concertHoldOpenDate.minusDays(5).plusHours(19);
	}
}
