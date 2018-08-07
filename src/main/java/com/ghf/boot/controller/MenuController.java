package com.ghf.boot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {
	@RequestMapping(value = "/menu", method = { RequestMethod.GET })
	public ModelAndView dmp(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("ordermenu");
		return mv;
	}
	
	@RequestMapping(value = "/menuManager", method = { RequestMethod.GET })
	public ModelAndView manage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("menumanager");
		return mv;
	}
}
