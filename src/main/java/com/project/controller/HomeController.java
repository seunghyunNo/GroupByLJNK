package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping("/")
	public String home(){
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public void home(Model model){
	}
	
	@RequestMapping("/apitest")
	public void test1(){

	}
}
