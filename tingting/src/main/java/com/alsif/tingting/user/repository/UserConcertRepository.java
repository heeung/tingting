package com.alsif.tingting.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.user.entity.UserConcert;

@Repository
public interface UserConcertRepository extends JpaRepository<UserConcert, Long> {

	boolean existsByUser_SeqAndConcert_Seq(Long userSeq, Long concertSeq);

	void deleteByUser_SeqAndConcert_Seq(Long userSeq, Long concertSeq);
}
