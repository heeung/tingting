package com.alsif.book.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.book.user.entity.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
	Optional<Point> findTop1ByUser_SeqOrderBySeqDesc(Integer userSeq);
}
