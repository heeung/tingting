package com.alsif.tingting.dummy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.dummy.service.DummyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DummyController {

	private final DummyService dummyService;

	@GetMapping("/dummy")
	public void dummy() {
		log.info("컨트롤러 시작");
		dummyService.insertAllData();
	}

	@GetMapping("/dummy/seat-info")
	public void dummySeatInfoSingle(@RequestParam("start") int start, @RequestParam("end") int end) {
		log.info("컨트롤러 시작");
		dummyService.insertConcertSeatInfoSingle(start, end);
	}

	@GetMapping("/dummy/concerts")
	public void concerts() {
		log.info("컨트롤러 concerts 메서드 시작");
		dummyService.insertConcertsAndConcertSeatInfos();
	}

	@GetMapping("/dummy/users")
	public void users() {
		log.info("컨트롤러 users 메서드 시작");
		dummyService.initAndInsertUsers();
	}

	/*@GetMapping("/dummy/grades")
	public void grades(@RequestParam("start") long start, @RequestParam("end") long end) {
		log.info("컨트롤러 grades 메서드 시작");
		dummyService.insertGradeSingle(start, end);
	}*/

	@GetMapping("/dummy/concert-hall-seat")
	public void concertHallSeat() {
		log.info("컨트롤러 insertConcertHallSeats 메서드 시작");
		dummyService.insertConcertHallSeats();
	}

	@GetMapping("/dummy/batch-seat-info")
	public void batchSeatInfo(@RequestParam("start") long start, @RequestParam("end") long end) {
		log.info("batch insert 컨트롤러 시작");
		// dummyService.insertConcertSeatInfosBatch(start, end);
		log.info("batch insert 컨트롤러 종료");
	}
}
