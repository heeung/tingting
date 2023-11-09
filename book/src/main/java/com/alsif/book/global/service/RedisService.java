package com.alsif.book.global.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final StringRedisTemplate stringRedisTemplate;

	private final Long DEFAULT_TIMEOUT = 10L;

	/*
		만료 시간 설정
	 */
	private void setExpireTime(String hashKey, long minutes) {
		stringRedisTemplate.expire(hashKey, minutes, TimeUnit.MINUTES);
	}

	/*
		hash table 값 조회
	 */
	public String getValue(String hashKey, long minutes) {
		String value = stringRedisTemplate.opsForValue().get(hashKey);
		if (value != null) {
			setExpireTime(hashKey, minutes);
		}
		return value;
	}

	public String getValue(String hashKey) {
		return this.getValue(hashKey, DEFAULT_TIMEOUT);
	}

	/*
		hash table 값 설정
	 */
	public void setValue(String hashKey, String value, long minutes) {
		stringRedisTemplate.opsForValue().set(hashKey, value);
		setExpireTime(hashKey, minutes);
	}

	public void setValue(String hashKey, String value) {
		this.setValue(hashKey, value, DEFAULT_TIMEOUT);
	}
}
