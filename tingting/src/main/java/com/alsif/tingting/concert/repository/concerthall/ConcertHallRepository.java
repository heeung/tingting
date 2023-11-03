package com.alsif.tingting.concert.repository.concerthall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.concerthall.ConcertHall;

@Repository
public interface ConcertHallRepository extends JpaRepository<ConcertHall, Integer> {
}
