package com.ghf.order.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.ghf.order.bean.MenuInfo;


@Service
public class UserService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	public void set(String key, MenuInfo user) {
		redisTemplate.opsForValue().set(key, user);
	}

	public MenuInfo get(String key) {
		return (MenuInfo) redisTemplate.boundValueOps(key).get();
	}

	public void setCode(String key, String code) {
		stringRedisTemplate.opsForValue().set(key, code, 60, TimeUnit.SECONDS);
	}

	public String getCode(String key) {
		return stringRedisTemplate.boundValueOps(key).get();
	}
}
