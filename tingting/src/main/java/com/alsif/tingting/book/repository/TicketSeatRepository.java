package com.alsif.tingting.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.book.entity.TicketSeat;
import com.alsif.tingting.concert.dto.concerthall.SeatBaseDto;

@Repository
public interface TicketSeatRepository extends JpaRepository<TicketSeat, Long> {

	@Query("SELECT NEW com.alsif.tingting.concert.dto.concerthall.SeatBaseDto(chs.section, chs.seat, g.price) "
		+ "FROM TicketSeat ts "
		+ "    JOIN ConcertSeatInfo csi ON (ts.concertSeatInfo.seq = csi.seq) "
		+ "    JOIN ConcertHallSeat chs ON (csi.concertHallSeat.seq = chs.seq) "
		+ "    JOIN Grade g ON (csi.grade.seq = g.seq) "
		+ "WHERE ts.ticket.seq = :ticketSeq")
	List<SeatBaseDto> findAllByTicketSeq(@Param("ticketSeq") Long ticketSeq);
}
