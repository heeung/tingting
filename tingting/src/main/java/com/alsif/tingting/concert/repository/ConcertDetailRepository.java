package com.alsif.tingting.concert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.ConcertDetail;

@Repository
public interface ConcertDetailRepository extends JpaRepository<ConcertDetail, Integer> {

	List<ConcertDetail> findAllByConcert_Seq(Integer concertSeq);
}
