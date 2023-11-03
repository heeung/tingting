package com.alsif.tingting.concert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.performer.PerformerBaseDto;
import com.alsif.tingting.concert.entity.performer.ConcertPerformer;

@Repository
public interface ConcertPerformerRepository extends JpaRepository<ConcertPerformer, Integer> {

	@Query("SELECT NEW com.alsif.tingting.concert.dto.performer.PerformerBaseDto(p.seq, p.name, p.imageUrl) "
		+ "FROM ConcertPerformer cp "
		+ "JOIN Performer p ON (cp.performer.seq = p.seq) "
		+ "WHERE cp.concert.seq = :concertSeq")
	List<PerformerBaseDto> findAllByConcertSeq(@Param("concertSeq") Integer concertSeq);
}
