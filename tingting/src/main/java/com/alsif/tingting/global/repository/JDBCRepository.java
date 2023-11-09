package com.alsif.tingting.global.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alsif.tingting.book.entity.Ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JDBCRepository {

	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public void saveAllTicketSeat(List<Long> concertSeatInfoSeqs, Ticket ticket) {
		String sql = "INSERT INTO ticket_seat (ticket_seq, concert_seat_info_seq) "
			+ "VALUES (?, ?)";

		jdbcTemplate.batchUpdate(sql,
			concertSeatInfoSeqs,
			concertSeatInfoSeqs.size(),
			(PreparedStatement ps, Long seq) -> {
				ps.setLong(1, seq);
				ps.setInt(2, ticket.getSeq());
			});
	}

}
