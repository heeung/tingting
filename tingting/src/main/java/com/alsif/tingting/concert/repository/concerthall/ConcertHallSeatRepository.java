package com.alsif.tingting.concert.repository.concerthall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.concerthall.ConcertHallSeat;

@Repository
public interface ConcertHallSeatRepository extends JpaRepository<ConcertHallSeat, Integer> {
}
