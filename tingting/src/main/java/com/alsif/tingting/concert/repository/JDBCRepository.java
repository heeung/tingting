package com.alsif.tingting.concert.repository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.Concert;
import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;
import com.alsif.tingting.concert.entity.Grade;
import com.alsif.tingting.concert.entity.performer.ConcertPerformer;
import com.alsif.tingting.user.entity.Point;
import com.alsif.tingting.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JDBCRepository {

	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public void saveAllConcertSeatInfo(List<ConcertSeatInfo> concertSeatInfos) {
		String sql = "INSERT INTO concert_seat_info (book, concert_detail_seq, concert_hall_seat_seq, grade_seq) "
			+ "VALUES (?, ?, ?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			concertSeatInfos,
			concertSeatInfos.size(),
			(PreparedStatement ps, ConcertSeatInfo concertSeatInfo) -> {
				ps.setBoolean(1, concertSeatInfo.getBook());
				ps.setInt(2, concertSeatInfo.getConcertDetail().getSeq());
				ps.setInt(3, concertSeatInfo.getConcertHallSeat().getSeq());
				ps.setInt(4, concertSeatInfo.getGrade().getSeq());
			});

		long l1 = System.currentTimeMillis();
		log.info("concertSeatInfos {}건 완료, 걸린시간: {}ms", concertSeatInfos.size(), l1 - l);
	}

	@Transactional
	public void saveAllConcert(List<Concert> concerts) {
		String sql =
			"INSERT INTO concert (concert_hall_seq, name, info, image_url, hold_open_date, hold_close_date, book_open_date, book_close_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			concerts,
			concerts.size(),
			(PreparedStatement ps, Concert concert) -> {
				ps.setInt(1, concert.getConcertHall().getSeq());
				ps.setString(2, concert.getName());
				ps.setString(3, concert.getInfo());
				ps.setString(4, concert.getImageUrl());
				ps.setString(5, concert.getHoldOpenDate().toString());
				ps.setString(6, concert.getHoldCloseDate().toString());
				ps.setString(7, concert.getBookOpenDate().toString());
				ps.setString(8, concert.getBookCloseDate().toString());
			});

		long l1 = System.currentTimeMillis();
		log.info("concert {}건 완료, 걸린시간: {}ms", concerts.size(), l1 - l);
	}

	@Transactional
	public void saveAllConcertDetail(List<ConcertDetail> concertDetails) {
		String sql =
			"INSERT INTO concert_detail (concert_seq, hold_date) "
				+ "VALUES (?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			concertDetails,
			concertDetails.size(),
			(PreparedStatement ps, ConcertDetail concertDetail) -> {
				ps.setInt(1, concertDetail.getConcert().getSeq());
				ps.setString(2, concertDetail.getHoldDate().toString());
			});

		long l1 = System.currentTimeMillis();
		log.info("concertDetails {}건 완료, 걸린시간: {}ms", concertDetails.size(), l1 - l);
	}

	@Transactional
	public void saveAllGrade(List<Grade> grades) {
		String sql =
			"INSERT INTO grade (concert_seq, name, price) "
				+ "VALUES (?, ?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			grades,
			grades.size(),
			(PreparedStatement ps, Grade grade) -> {
				ps.setInt(1, grade.getConcert().getSeq());
				ps.setString(2, grade.getName());
				ps.setInt(3, grade.getPrice());
			});

		long l1 = System.currentTimeMillis();
		log.info("grades {}건 완료, 걸린시간: {}ms", grades.size(), l1 - l);
	}

	@Transactional
	public void saveAllConcertPerformer(List<ConcertPerformer> concertPerformers) {
		String sql =
			"INSERT INTO concert_perfomer (concert_seq, performer_seq) "
				+ "VALUES (?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			concertPerformers,
			concertPerformers.size(),
			(PreparedStatement ps, ConcertPerformer concertPerformer) -> {
				ps.setInt(1, concertPerformer.getConcert().getSeq());
				ps.setInt(2, concertPerformer.getPerformer().getSeq());
			});

		long l1 = System.currentTimeMillis();
		log.info("concertPerformer {}건 완료, 걸린시간: {}ms", concertPerformers.size(), l1 - l);
	}

	@Transactional
	public void saveAllUsers(List<User> users) {
		String sql =
			"INSERT INTO `user` (email, create_date, last_modified_date) "
				+ "VALUES (?, ?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			users,
			users.size(),
			(PreparedStatement ps, User user) -> {
				ps.setString(1, user.getEmail());
				ps.setString(2, LocalDateTime.now().toString());
				ps.setString(3, LocalDateTime.now().toString());
			});

		long l1 = System.currentTimeMillis();
		log.info("user {}건 완료, 걸린시간: {}ms", users.size(), l1 - l);
	}

	@Transactional
	public void saveAllPoints(List<Point> points) {
		String sql =
			"INSERT INTO point (create_date, last_modified_date, pay, total, user_seq) "
				+ "VALUES (?, ?, ?, ?, ?)";

		long l = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql,
			points,
			points.size(),
			(PreparedStatement ps, Point point) -> {
				ps.setString(1, LocalDateTime.now().toString());
				ps.setString(2, LocalDateTime.now().toString());
				ps.setInt(3, 10000000);
				ps.setInt(4, 10000000);
				ps.setInt(5, point.getUser().getSeq());
			});

		long l1 = System.currentTimeMillis();
		log.info("point {}건 완료, 걸린시간: {}ms", points.size(), l1 - l);
	}
}
