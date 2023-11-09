package com.alsif.book.concert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.book.concert.dto.ConcertSeatGradeInfoBaseDto;
import com.alsif.book.concert.entity.ConcertSeatInfo;

@Repository
public interface ConcertSeatInfoRepository extends JpaRepository<ConcertSeatInfo, Long> {

	@Query("SELECT NEW com.alsif.book.concert.dto.ConcertSeatGradeInfoBaseDto(csi.seq, csi.book, g.price) "
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
