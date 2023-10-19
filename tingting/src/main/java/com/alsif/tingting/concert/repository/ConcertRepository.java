package com.alsif.tingting.concert.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.ConcertBaseDto;
import com.alsif.tingting.concert.entity.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE c.bookOpenDate < :current AND :current <= c.bookCloseDate "
			+ "ORDER BY c.bookOpenDate DESC")
	Page<ConcertBaseDto> findAllConcertNow(@Param("current") LocalDateTime current, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE :current <= c.bookOpenDate "
			+ "ORDER BY c.bookOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertSoon(@Param("current") LocalDateTime current, Pageable pageable);
}
