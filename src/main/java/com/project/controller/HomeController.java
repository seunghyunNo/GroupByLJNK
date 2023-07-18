package com.project.controller;

import com.project.service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ApiServiceImpl apiService;
	
	@RequestMapping("/")
	public String home(){
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public void home(Model model){
	}
	
	@RequestMapping("/review/{appId}")
	public String review(@PathVariable String appId, Model model){
		model.addAttribute("appId", appId);
		
		var serviceResult = apiService.getData(appId);
		LinkedHashMap<?,?> body = (LinkedHashMap) ((LinkedHashMap) serviceResult.getBody()).get(appId);
		String status = serviceResult.getStatusCode().toString();
		
		model.addAttribute("status", status);
		if (body.get("success").toString().equals("true")) {
			model.addAttribute("data", body.get("data"));
		}
		
		return "review";
	}
	
}
