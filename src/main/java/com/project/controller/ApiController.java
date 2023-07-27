package com.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private ApiService apiService;
	
	private final String API_KEY = "FB50E2191E8E06A7CA8BCC63648DEB93";
	
	@GetMapping("/gameList")
	public String callApi() throws IOException {
		StringBuilder result = new StringBuilder();
		
		String urlStr = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/";
		
		URL url = new URL(urlStr);
		
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		
		
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
		
		String returnLine;
		while((returnLine = br.readLine()) != null){
			result.append(returnLine+"\n\r");
		}
		urlConnection.disconnect();
		
		return result.toString();
	} // end callApi() gameList
}
