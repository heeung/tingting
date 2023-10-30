package com.alsif.tingting.dummy.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
}
