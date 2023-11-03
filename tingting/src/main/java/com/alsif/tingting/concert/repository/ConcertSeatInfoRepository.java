package com.alsif.tingting.concert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.ConcertSeatInfo;

@Repository
public interface ConcertSeatInfoRepository extends JpaRepository<ConcertSeatInfo, Integer> {
	List<ConcertSeatInfo> findConcertSeatInfoByConcertDetail_Seq(Integer seq);

}
