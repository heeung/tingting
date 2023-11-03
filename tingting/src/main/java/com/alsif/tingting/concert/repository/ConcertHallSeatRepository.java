package com.alsif.tingting.concert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoResponseDto;
import com.alsif.tingting.concert.entity.concerthall.ConcertHallSeat;

@Repository
public interface ConcertHallSeatRepository extends JpaRepository<ConcertHallSeat, Long> {

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoResponseDto(csi.seq, chs.section, chs.seat, csi.book, g.name) "
			+ "FROM ConcertHallSeat chs "
			+ "JOIN ConcertSeatInfo csi ON (chs.seq = csi.concertHallSeat.seq) "
			+ "JOIN Grade g ON (csi.grade.seq = g.seq) "
			+ "WHERE chs.concertHall.seq = :concertHallSeq AND chs.section = :section AND csi.concertDetail.seq = :concertDetailSeq")
	List<ConcertSectionSeatInfoResponseDto> findAllSectionSeatInfo(@Param("concertDetailSeq") Long concertDetailSeq,
		@Param("concertHallSeq") Long concertHallSeq, @Param("section") String section);
}
