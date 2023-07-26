package com.project.controller;

import com.project.config.PrincipalDetails;
import com.project.domain.User;
import com.project.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ApiService apiService;
	
	@RequestMapping("/")
	public String home(){
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public void home(Model model){
		try{
			PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long id = user.getId();
			model.addAttribute("logged_id", id);
		} catch (Exception e){
			model.addAttribute("logged_id", null);
		}
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
		
		model.addAttribute("src","https://cdn.akamai.steamstatic.com/steam/apps/" + appId + "/header.jpg");
		
		try{
			PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long id = user.getId();
			model.addAttribute("logged_id", id);
		} catch (Exception e){
			model.addAttribute("logged_id", null);
		}
		
		return "review";
	}
	
}
