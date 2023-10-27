package com.alsif.tingting.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.entity.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
