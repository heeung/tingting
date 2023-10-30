package com.alsif.tingting.dummy.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alsif.tingting.book.entity.Ticket;
import com.alsif.tingting.book.entity.TicketSeat;
import com.alsif.tingting.book.repository.TicketRepository;
import com.alsif.tingting.book.repository.TicketSeatRepository;
import com.alsif.tingting.concert.entity.Concert;
import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;
import com.alsif.tingting.concert.entity.Grade;
import com.alsif.tingting.concert.entity.concerthall.ConcertHall;
import com.alsif.tingting.concert.entity.concerthall.ConcertHallSeat;
import com.alsif.tingting.concert.entity.performer.ConcertPerformer;
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
import com.alsif.tingting.user.entity.UserConcert;
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

	List<Performer> performers;
	List<ConcertHall> concertHalls;
	List<ConcertHallSeat> concertHallSeats;
	List<User> users;
	List<Point> points;
	List<Concert> concerts;
	List<ConcertDetail> concertDetails;
	List<Grade> grades;
	List<ConcertPerformer> concertPerformers;
	List<ConcertSeatInfo> concertSeatInfos;
	List<Ticket> tickets;
	List<TicketSeat> ticketSeats;

	List<UserConcert> userConcerts;

	public void insertAllData() {
		dummyList = new DummyList();
		init();
		insertPerformers();
		insertConcertHalls();
		insertUsers();
		insertConcerts();
		insertConcertSeatInfos();
		insertTicket();
		insertFavorite();
	}

	private void init() {
		performers = new ArrayList<>();
		concertHalls = new ArrayList<>();
		concertHallSeats = new ArrayList<>();
		users = new ArrayList<>();
		concerts = new ArrayList<>();
		concertDetails = new ArrayList<>();
		grades = new ArrayList<>();
		concertPerformers = new ArrayList<>();
		concertSeatInfos = new ArrayList<>();
		tickets = new ArrayList<>();
		ticketSeats = new ArrayList<>();
		points = new ArrayList<>();
		userConcerts = new ArrayList<>();
	}

	// 가수 넣기
	@Transactional
	public void insertPerformers() {
		log.info("insertPerformers 시작");
		List<String> singer = dummyList.getPerformers();
		List<String> performersImage = dummyList.getPerformersImage();

		makePerformers(singer, performersImage);

		performerRepository.saveAll(performers);
		log.info("insertPerformers 종료");

	}

	// 콘서트홀, 콘서트홀 좌석 넣기
	@Transactional
	public void insertConcertHalls() {
		log.info("insertConcertHalls 시작");
		List<String> concertHallNames = dummyList.getConcertHallName();
		List<String> concertHallCities = dummyList.getConcertHallCity();

		makeConcertHalls(concertHallNames, concertHallCities);
		makeConcertHallSeats();

		concertHallRepository.saveAll(concertHalls);
		concertHallSeatRepository.saveAll(concertHallSeats);
		log.info("insertConcertHalls 종료");
	}

	// 회원정보, 포인트(회원가입 때 주는거) 정보 넣기
	@Transactional
	public void insertUsers() {
		log.info("insertUsers 시작");
		List<String> emails = dummyList.getEmails();

		makeUsersAndPoints(emails);

		userRepository.saveAll(users);
		pointRepository.saveAll(points);
		log.info("insertUsers 종료");
	}

	// 콘서트정보, 상세, 콘서트출연자, 등급
	@Transactional
	public void insertConcerts() {
		log.info("insertConcerts 시작");
		List<String> concertNameHeaders = dummyList.getConcertNameHeaders();
		List<String> concertNameMiddles = dummyList.getConcertNameMiddles();
		List<String> concertNameTails = dummyList.getConcertNameTails();
		List<String> concertInfos = dummyList.getConcertInfo();
		List<String> concertImageUrls = dummyList.getConcertImageUrls();

		makeConcertsWithDetailAndPerformersAndGrades(concertNameHeaders, concertNameMiddles, concertNameTails,
			concertInfos, concertImageUrls);

		concertRepository.saveAll(concerts);
		concertDetailRepository.saveAll(concertDetails);
		concertPerformerRepository.saveAll(concertPerformers);
		gradeRepository.saveAll(grades);
		log.info("insertConcerts 종료");
	}

	// 콘서트 좌석 정보 넣기
	@Transactional
	public void insertConcertSeatInfos() {
		log.info("insertConcertSeatInfos 시작");
		for (Concert concert : concerts) {
			List<ConcertDetail> concertDetailsByConcert = concert.getConcertDetails();
			List<Grade> gradesByConcert = concert.getGrades();
			List<ConcertHallSeat> concertHallSeatsByConcert = concert.getConcertHall().getConcertHallSeats();
			for (ConcertDetail concertDetail : concertDetailsByConcert) {
				if (gradesByConcert.size() == 1) {
					for (ConcertHallSeat concertHallSeat : concertHallSeatsByConcert) {
						ConcertSeatInfo concertSeatInfo = ConcertSeatInfo.builder()
							.concertDetail(concertDetail)
							.book(false)
							.grade(gradesByConcert.get(0))
							.concertHallSeat(concertHallSeat)
							.build();
						concertSeatInfos.add(concertSeatInfo);
					}
				} else {
					Grade vip = gradesByConcert.get(0).getName().equals("VIP") ? gradesByConcert.get(0) :
						gradesByConcert.get(1);
					Grade common =
						gradesByConcert.get(1).getName().equals("일반") ? gradesByConcert.get(1) : gradesByConcert.get(0);
					for (ConcertHallSeat concertHallSeat : concertHallSeatsByConcert) {
						ConcertSeatInfo concertSeatInfo;
						if (concertHallSeat.getSection().charAt(0) <= 'E') {
							concertSeatInfo = ConcertSeatInfo.builder()
								.concertDetail(concertDetail)
								.book(false)
								.grade(vip)
								.concertHallSeat(concertHallSeat)
								.build();
						} else {
							concertSeatInfo = ConcertSeatInfo.builder()
								.concertDetail(concertDetail)
								.book(false)
								.grade(common)
								.concertHallSeat(concertHallSeat)
								.build();
						}
						concertSeatInfos.add(concertSeatInfo);
					}
				}
			}
		}

		concertSeatInfoRepository.saveAll(concertSeatInfos);
		log.info("insertConcertSeatInfos 종료");
	}

	@Transactional
	public void insertTicket() {

		Ticket ticket = null;

		// 티켓 백장 만들기
		for (int i = 0; i < 100000; i++) {

			User randomUser = getRandomValue(users);
			ConcertDetail randomConcertDetail = getRandomValue(concertDetails);

			ticket = Ticket.builder()
				.user(randomUser)
				.concertDetail(randomConcertDetail)
				.build();

			// 랜덤으로 넣은 콘서트 상세 좌석정보목록리스트
			List<ConcertSeatInfo> concertSeatInfoList = concertSeatInfoRepository.findConcertSeatInfoByConcertDetail_Seq(
				randomConcertDetail.getSeq());

			// 예매좌석정보 생성
			// 한개의 예매할 때 1~4개 좌석 선택
			int cnt = (int)(Math.random() * 4) + 1;
			cnt = setTicketSeat(ticket, concertSeatInfoList, cnt);
			if (cnt > 0) {
				continue;
			}

			// 담은 예매좌석정보수만큼 가격구하고 해당 예매와 관련한 포인트 제거하기
			// 해당 좌석 포인트
			long totalPrice = 0L;
			for (TicketSeat seat : ticket.getTicketSeats()) {
				totalPrice += seat.getConcertSeatInfo().getGrade().getPrice();
			}

			// 나의 최근 포인트
			long latestTotal = pointRepository.findTop1ByUserSeqOrderByCreatedDateDesc(randomUser.getSeq()).getTotal();

			// 포인트 추가
			Point point = Point.builder()
				.user(randomUser)
				.ticket(ticket)
				.pay(totalPrice)
				.total(latestTotal - totalPrice)
				.build();

			ticket.addPoint(point);

			points.add(point);
			tickets.add(ticket);
		}

		ticketRepository.saveAll(tickets);
		pointRepository.saveAll(points);
		ticketSeatRepository.saveAll(ticketSeats);

	}

	// 찜
	@Transactional
	public void insertFavorite() {
		for (int i = 0; i < 100; i++) {
			UserConcert userConcert = UserConcert.builder()
				.user(getRandomValue(users))
				.concert(getRandomValue(concerts))
				.build();

			userConcerts.add(userConcert);
		}
		userConcertRepository.saveAll(userConcerts);
	}

	private int setTicketSeat(Ticket ticket, List<ConcertSeatInfo> concertSeatInfoList, int cnt) {
		for (int j = 0; j < concertSeatInfoList.size(); j++) {
			if (cnt == 0) {
				break;
			}
			ConcertSeatInfo concertSeatInfo = concertSeatInfoList.get(j);
			if (!concertSeatInfo.getBook()) {
				// 예매 좌석 정보
				TicketSeat ticketSeat = TicketSeat.builder()
					.ticket(ticket)
					.concertSeatInfo(concertSeatInfo)
					.build();

				// 해당 콘서트 좌석 정보 예매 완료
				concertSeatInfo.updateStatus();

				ticket.addTicketSeats(ticketSeat);
				ticketSeats.add(ticketSeat);

				cnt--;
			}
		}
		return cnt;
	}

	private void makePerformers(List<String> singer, List<String> performersImage) {
		for (int i = 0; i < 100; i++) {
			Performer performer = Performer.builder()
				.name(singer.get(i))
				.imageUrl(performersImage.get(i))
				.build();
			performers.add(performer);
		}
	}

	private void makeConcertHallSeats() {
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

	private void makeConcertHalls(List<String> concertHallNames, List<String> concertHallCities) {
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

	// user의 createdate가 default라서 이거 안씀 일단
	private LocalDateTime makeRandomDate() {
		int startDate = 1_597_581_600; // 2020년 8월 16일 21시 40분 00초
		int endDate = 1_647_581_600; // 2022년 3월 18일 14시 33분 20초

		int unixTime = (int)((endDate - startDate) * Math.random()) + startDate;

		return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Asia/Seoul"));
	}

	private void makeUsersAndPoints(List<String> emails) {
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
		return String.format("%s %s %s", getRandomValue(concertNameHeaders), getRandomValue(concertNameMiddles),
			getRandomValue(concertNameTails));
	}

	private <T> T getRandomValue(List<T> list) {
		return list.get(getRandomListIndex(list.size()));
	}

	private int getRandomListIndex(int listSize) {
		return (int)(Math.random() * listSize);
	}

	private LocalDateTime makeRandomConcertHoldOpenDate() {
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

	private void makeConcertsWithDetailAndPerformersAndGrades(List<String> concertNameHeaders,
		List<String> concertNameMiddles,
		List<String> concertNameTails, List<String> concertInfos, List<String> concertImageUrls) {
		for (int i = 0; i < 500; i++) {
			String concertName = makeConcertName(concertNameHeaders, concertNameMiddles, concertNameTails);
			String concertInfo = getRandomValue(concertInfos);
			String concertImageUrl = getRandomValue(concertImageUrls);

			int concertHoldPeriod = (int)(Math.random() * 5) + 1;

			LocalDateTime concertHoldOpenDate = makeRandomConcertHoldOpenDate();
			LocalDateTime concertHoldCloseDate = makeConcertHoldCloseDate(concertHoldOpenDate, concertHoldPeriod);
			LocalDateTime concertBookOpenDate = makeConcertBookOpenDate(concertHoldOpenDate);
			LocalDateTime concertBookCloseDate = makeConcertBookCloseDate(concertHoldOpenDate);

			Concert concert = Concert.builder()
				.concertHall(getRandomValue(concertHalls))
				.name(concertName)
				.info(concertInfo)
				.imageUrl(concertImageUrl)
				.holdOpenDate(concertHoldOpenDate)
				.holdCloseDate(concertHoldCloseDate)
				.bookOpenDate(concertBookOpenDate)
				.bookCloseDate(concertBookCloseDate)
				.build();

			makeConcertPerformer(concert);

			makeConcertDetail(concertHoldPeriod, concertHoldOpenDate, concert);

			makeConcertGrade(concert);

			concerts.add(concert);
		}
	}

	private void makeConcertPerformer(Concert concert) {
		ConcertPerformer concertPerformer = ConcertPerformer.builder()
			.performer(getRandomValue(performers))
			.build();
		concertPerformer.setConcert(concert);
		concertPerformers.add(concertPerformer);
	}

	private void makeConcertGrade(Concert concert) {
		int gradeRandom = (int)(Math.random() * 2) + 1;
		long priceRandom = (long)(Math.random() * 2) + 5;
		for (int j = 0; j < gradeRandom; j++) {
			Grade grade;
			if (j == 0) {
				grade = Grade.builder()
					.price(priceRandom * 11000L)
					.name("일반")
					.build();
			} else {
				grade = Grade.builder()
					.price((priceRandom + 2) * 11000L)
					.name("VIP")
					.build();
			}
			grade.setConcert(concert);
			grades.add(grade);
		}
	}

	private void makeConcertDetail(int concertHoldPeriod, LocalDateTime concertHoldOpenDate, Concert concert) {
		for (int j = 0; j < concertHoldPeriod; j++) {
			ConcertDetail concertDetail = ConcertDetail.builder()
				.holdDate(concertHoldOpenDate.plusDays(j).plusHours(19))
				.build();
			concertDetail.setConcert(concert);
			concertDetails.add(concertDetail);
		}
	}
}
