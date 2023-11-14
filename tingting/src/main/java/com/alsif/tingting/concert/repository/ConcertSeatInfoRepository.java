package com.alsif.tingting.concert.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.ConcertSeatGradeInfoBaseDto;
import com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto;
import com.alsif.tingting.concert.entity.ConcertSeatInfo;

@Repository
public interface ConcertSeatInfoRepository extends JpaRepository<ConcertSeatInfo, Long> {

	@Query("SELECT NEW com.alsif.tingting.concert.dto.concerthall.SeatBookBaseDto(csi.book) "
		+ "FROM ConcertSeatInfo csi "
		+ "WHERE csi.concertDetail.seq = :concertDetailSeq AND csi.concertHallSeat.seq = :concertHallSeatSeq")
	Optional<SeatBookBaseDto> findBookByConcertDetail_SeqAAndConcertHallSeat_Seq(
		@Param("concertDetailSeq") Integer concertDetailSeq, @Param("concertHallSeatSeq") Integer concertHallSeatSeq);

	Optional<ConcertSeatInfo> findByConcertDetail_SeqAndConcertHallSeat_Seq(
		Integer concertDetailSeq, Integer concertHallSeatSeq);

	@Query("SELECT NEW com.alsif.tingting.concert.dto.ConcertSeatGradeInfoBaseDto(csi.seq, csi.book, g.price) "
		+ "FROM ConcertSeatInfo csi "
		+ "JOIN Grade g ON csi.grade.seq = g.seq "
		+ "WHERE csi.seq in :seqs")
	List<ConcertSeatGradeInfoBaseDto> findByConcertSeatInfoJoinGrade(@Param("seqs") List<Long> concertSeatInfos);

	@Modifying
	@Query("UPDATE ConcertSeatInfo "
		+ "SET book = :bool "
		+ "WHERE seq in :seqs")
	int updateBooks(@Param("seqs") List<Long> concertSeatInfoSeqs, @Param("bool") Boolean bool);
}
