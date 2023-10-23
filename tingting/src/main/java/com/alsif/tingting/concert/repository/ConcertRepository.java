package com.alsif.tingting.concert.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.concert.dto.ConcertBaseDto;
import com.alsif.tingting.concert.entity.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE c.bookOpenDate < :current AND :current <= c.bookCloseDate "
			+ "ORDER BY c.bookOpenDate DESC")
	Page<ConcertBaseDto> findAllConcertNow(@Param("current") LocalDateTime current, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE :current <= c.bookOpenDate "
			+ "ORDER BY c.bookOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertSoon(@Param("current") LocalDateTime current, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE ch.city = :place AND c.name LIKE %:searchWord% AND c.holdOpenDate >= :startDate AND c.holdCloseDate < :endDate "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertByPlaceAndSearchWordAndDate(
		@Param("place") String place, @Param("searchWord") String searchWord,
		@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE ch.city = :place AND c.name LIKE %:searchWord% "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertByPlaceAndSearchWord(
		@Param("place") String place, @Param("searchWord") String searchWord, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE ch.city = :place AND c.holdOpenDate >= :startDate AND c.holdCloseDate < :endDate "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertByPlaceAndDate(
		@Param("place") String place, @Param("startDate") LocalDateTime startDate,
		@Param("endDate") LocalDateTime endDate, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE ch.city = :place "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertByPlace(
		@Param("place") String place, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE c.name LIKE %:searchWord% AND c.holdOpenDate >= :startDate AND c.holdCloseDate < :endDate "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertBySearchWordAndDate(
		@Param("searchWord") String searchWord, @Param("startDate") LocalDateTime startDate,
		@Param("endDate") LocalDateTime endDate, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE c.name LIKE %:searchWord% "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertBySearchWord(
		@Param("searchWord") String searchWord, Pageable pageable);

	@Query(
		"SELECT NEW com.alsif.tingting.concert.dto.ConcertBaseDto(c.seq, c.name, c.holdOpenDate, c.holdCloseDate, c.imageUrl, ch.name, ch.city) "
			+ "FROM Concert c "
			+ "JOIN ConcertHall ch ON c.concertHall.seq = ch.seq "
			+ "WHERE c.holdOpenDate >= :startDate AND c.holdCloseDate < :endDate "
			+ "ORDER BY c.holdOpenDate ASC")
	Page<ConcertBaseDto> findAllConcertByDate(
		@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
