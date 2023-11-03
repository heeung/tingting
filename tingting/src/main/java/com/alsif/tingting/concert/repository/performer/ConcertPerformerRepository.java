package com.alsif.tingting.concert.repository.performer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.performer.ConcertPerformer;

@Repository
public interface ConcertPerformerRepository extends JpaRepository<ConcertPerformer, Integer> {
}
