package com.alsif.tingting.dummy.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;
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



	/**
	 * 1. 가수를 넣는다. (이 행위는 데이터 넣는 첫날에만 발생함
	 * 2. 콘서트홀, 콘서트 좌석을 넣는다. 근데 콘서트홀 좌석 패턴에 대해서 아직 얘기를 안해봤는데? 흠......
	 * -> 이걸 할 때 연관관계를 걸어줘야하기 때문에, Entity하나 만들고, 그에 종속되는 Entity를 만든다음 매핑 해서 인서트해야됨
	 * 3. 회원정보, 포인트 정보 넣기
	 * 4. 콘서트 정보, 상세, 콘서트 출연자 넣기
	 * 5. 콘서트 좌석 정보 넣기
	 * 6. 예매 티켓 정보 넣기(보류)
	 *
	 * 근데 여기서 중요한건 현재 메서드별로 리턴값 void로 해놨는데, 특정 메서드는 분리 및 합체 되면서
	 * 리턴값을 엔티티를 받아다가 그걸로 추가적으로 매핑하거나 하는 연산이 필요할 수도 있음.
	 * -> 전역변수 선언하는 걸로 수정
	 */

	// @Transactional
	public void insertAllData() {
		dummyList = new DummyList();
		init();
		insertPerformers();
		insertConcertHalls();
		insertUsers();
		insertConcerts();
		insertConcertSeatInfos();
	}

	private void init() {
		performers = new ArrayList<>();
		concertHalls = new ArrayList<>();
		concertHallSeats = new ArrayList<>();
		users = new ArrayList<>();
		points = new ArrayList<>();
		concerts = new ArrayList<>();
		concertDetails = new ArrayList<>();
		grades = new ArrayList<>();
		concertPerformers = new ArrayList<>();
		concertSeatInfos = new ArrayList<>();
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


	// 예매 티켓 정보 넣기
	// TODO: 보류
	@Transactional
	public void insertTickets() {
		tickets = new ArrayList<>();
		ticketSeats = new ArrayList<>();
		points = new ArrayList<>();

		List<User> users = userRepository.findAll();
		List<ConcertDetail> concertDetails = concertDetailRepository.findAll();
		List<ConcertSeatInfo> concertSeatInfos = concertSeatInfoRepository.findAll();


		int userSize = users.size();
		int concertDetailSize = concertDetails.size();
		int concertSeatInfoSize = concertSeatInfos.size();

		for(int i = 0 ; i < 100 ; i++) {

			Ticket ticket = Ticket.builder()
				.user(getRandomValue(users))
				.concertDetail(getRandomValue(concertDetails))
				.build();

			// concertSeatInfo 돌면서 좌석 있는곳에 true
			// concertDetail이 동일한 곳에


			User choiceUser = ticket.getUser();
			ConcertDetail choiceConcertDetail = ticket.getConcertDetail();

			ConcertSeatInfo concertSeatInfo = null;
			while(true){
				concertSeatInfo = getRandomValue(concertSeatInfos);
				if(concertSeatInfo.getConcertDetail().equals(choiceConcertDetail) && !concertSeatInfo.getBook()){
					break;
				}
			}


			// 포인트 만들기
			// 근데 포인트할때 그 좌석의 가격을 알아야함
			// 나의 제일 최근 포인트를 알아야함
			// 포인트를 돌면서 회원이 동일하고 생성일이 가장 최근인 row 찾아서 그때의 포인트 누적 찾기

			List<Point> pointsList = pointRepository.findAll();

			// 내 포인트의 최근찾기
			List<Point> myPoint = new ArrayList<>();
			for(Point point : pointsList){
				if(point.getUser().equals(choiceUser)){
					myPoint.add(point);
				}
			}
			myPoint.sort(Comparator.comparing(Point::getCreatedDate));
			Long latestPoint = myPoint.get(myPoint.size()-1).getTotal();

			Point point = Point.builder()
				.user(choiceUser)
				.ticket(ticket)
				.pay(concertSeatInfo.getGrade().getPrice())
				.total(latestPoint-concertSeatInfo.getGrade().getPrice())
				.build();


			TicketSeat ticketSeat = TicketSeat.builder()
				.ticket(ticket)
				.concertSeatInfo(concertSeatInfo)
				.build();

			concertSeatInfo.updateStatus();

			tickets.add(ticket);
			ticketSeats.add(ticketSeat);
			points.add(point);


		}
		ticketRepository.saveAll(tickets);
		ticketSeatRepository.saveAll(ticketSeats);
		pointRepository.saveAll(points);
		log.info("insertTicket 함수 종료");

	}



	// TODO : 예매 정보 추가 리팩토링
	@Transactional
	public void insertTicketRefact() {
		tickets = new ArrayList<>();        // 새로 만든 티켓들을 넣을 배열
		ticketSeats = new ArrayList<>();    // 새로 만든 예매 좌석 정보 넣을 배열
		points = new ArrayList<>();            // 새로 만든 포인트 넣을 배열

		List<User> users = userRepository.findAll();
		List<ConcertDetail> concertDetails = concertDetailRepository.findAll();
		List<ConcertSeatInfo> concertSeatInfos = concertSeatInfoRepository.findAll();
		Ticket ticket = null;



		// 티켓 백장 만들기
		for (int i = 0; i < 1; i++) {
			ticket = Ticket.builder()
				.user(getRandomValue(users))
				.concertDetail(getRandomValue(concertDetails))
				.build();


			// 랜덤으로 넣은 사용자와 콘서트상세
			User choiceUser = ticket.getUser();
			ConcertDetail choiceConcertDetail = ticket.getConcertDetail();

			// 랜덤으로 넣은 콘서트 상세 죄석정보목록리스트
			List<ConcertSeatInfo> concertSeatInfoList = concertSeatInfoRepository.findConcertSeatInfoByConcertDetail_Seq(
				choiceConcertDetail.getSeq());

			// 예매좌석정보 생성
			// 한개의 예매할 때 3개 좌석 선택
			int cnt = 3;
			for (ConcertSeatInfo concertSeatInfo : concertSeatInfoList) {
				if (cnt == 0) {
					break;
				}
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

			// 담은 예매좌석정보수만큼 가격구하고 해당 예매와 관련한 포인트 제거하기
			// 해당 좌석 포인트
			long totalPrice = 0L;
			for (TicketSeat seat : ticket.getTicketSeats()) {
				totalPrice += seat.getConcertSeatInfo().getGrade().getPrice();
			}

			// 나의 최근 포인트
			long latestTotal = pointRepository.findTop1ByUserSeqOrderByCreatedDateDesc(choiceUser.getSeq()).getTotal();


			// 포인트 추가
			Point point = Point.builder()
				.user(choiceUser)
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
	public void makeFavorite(){
		List<User> users = userRepository.findAll();
		List<Concert> concerts = concertRepository.findAll();
		userConcerts = new ArrayList<>();
		for(int i = 0 ; i < 100 ; i++){
			UserConcert userConcert = UserConcert.builder()
				.user(getRandomValue(users))
				.concert(getRandomValue(concerts))
				.build();

			userConcerts.add(userConcert);
		}
		userConcertRepository.saveAll(userConcerts);
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
