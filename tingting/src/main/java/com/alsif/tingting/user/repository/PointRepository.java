package com.alsif.tingting.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.user.entity.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {

	List<Point> findAllByUser_Seq(Integer seq);

	Point findTop1ByUserSeqOrderByCreatedDateDesc(Integer seq);
}
