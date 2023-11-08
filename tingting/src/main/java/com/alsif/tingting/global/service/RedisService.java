package com.alsif.tingting.global.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final StringRedisTemplate stringRedisTemplate;

	/*
		만료 시간 설정
	 */
	public void setExpireTime(String hashKey, long minutes) {
		stringRedisTemplate.expire(hashKey, minutes, TimeUnit.MINUTES);
	}

	/*
		hash table 값 조회
	 */
	public String getValue(String hashKey) {
		return stringRedisTemplate.opsForValue().get(hashKey);
	}

	/*
		hash table 값 설정
	 */
	public void setValue(String hashKey, String value) {
		stringRedisTemplate.opsForValue().set(hashKey, value);
	}

}
