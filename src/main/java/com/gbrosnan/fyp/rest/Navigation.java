package com.gbrosnan.fyp.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/")
public class Navigation {

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
    	return "static/html/fyp-home.html";
    }

	@RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats() {
    	return "static/html/stats.html";
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
    	return "static/html/user.html";
    }	
}
