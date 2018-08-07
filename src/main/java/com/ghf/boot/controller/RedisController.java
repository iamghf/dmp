package com.ghf.boot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ghf.order.bean.MenuInfo;
import com.ghf.order.service.UserService;

@RestController
public class RedisController {
	@Resource
	private UserService userService;

	@RequestMapping(value = "/setKey", method = { RequestMethod.GET })
	public String set() {
		userService.set("key1", new MenuInfo(1, "meepoguan", 26));
		return "success";
	}

	@RequestMapping(value = "/getKey", method = { RequestMethod.GET })
	public String get() {
		String name =  userService.get("key1").getName();
		return name;
	}
}
