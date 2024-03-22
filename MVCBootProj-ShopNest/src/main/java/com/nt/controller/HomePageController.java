package com.nt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
	
	@GetMapping("/")
	public String showHomePage() {
		//return LVN
		return "welcome";
	}

}
