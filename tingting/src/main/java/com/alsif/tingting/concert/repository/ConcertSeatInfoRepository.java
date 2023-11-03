package com.alsif.tingting.concert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;

@Repository
public interface ConcertSeatInfoRepository extends JpaRepository<ConcertSeatInfo, Integer> {

	@Query("SELECT NEW com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto(csi.book) "
		+ "FROM ConcertSeatInfo csi "
		+ "WHERE csi.concertDetail.seq = :concertDetailSeq AND csi.concertHallSeat.seq = :concertHallSeatSeq")
	Optional<SeatBookBaseDto> findBookByConcertDetail_SeqAAndConcertHallSeat_Seq(
		@Param("concertDetailSeq") Integer concertDetailSeq, @Param("concertHallSeatSeq") Integer concertHallSeatSeq);

	Optional<ConcertSeatInfo> findByConcertDetail_SeqAndConcertHallSeat_Seq(
		Integer concertDetailSeq, Integer concertHallSeatSeq);
}
