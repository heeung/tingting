package com.alsif.tingting.concert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;

@Repository
public interface ConcertSeatInfoRepository extends JpaRepository<ConcertSeatInfo, Long> {

	@Query("SELECT NEW com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto(csi.book) "
		+ "FROM ConcertSeatInfo csi "
		+ "WHERE csi.concertDetail.seq = :concertDetailSeq AND csi.concertHallSeat.seq = :concertHallSeatSeq")
	Optional<SeatBookBaseDto> findBookByConcertDetail_SeqAAndConcertHallSeat_Seq(
		@Param("concertDetailSeq") Long concertDetailSeq, @Param("concertHallSeatSeq") Long concertHallSeatSeq);

	Optional<ConcertSeatInfo> findByConcertDetail_SeqAndConcertHallSeat_Seq(
		Long concertDetailSeq, Long concertHallSeatSeq);
}
