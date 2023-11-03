package com.alsif.tingting.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.book.dto.TicketBaseDto;
import com.alsif.tingting.book.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query(
		"SELECT NEW com.alsif.tingting.book.dto.TicketBaseDto(t.seq, t.createdDate, t.deletedDate, p.pay, c.seq, c.name, cd.holdDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Ticket t "
			+ "    JOIN ConcertDetail cd ON (t.concertDetail.seq = cd.seq) "
			+ "    JOIN Concert c ON (cd.concert.seq = c.seq) "
			+ "    JOIN ConcertHall ch ON (c.concertHall.seq = ch.seq) "
			+ "    JOIN Point p ON (p.ticket.seq = t.seq) "
			+ "WHERE t.user.seq = :userSeq AND p.pay < 0")
	Page<TicketBaseDto> findAllByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);
}
