package com.alsif.tingting.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.performer.Performer;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {
}
