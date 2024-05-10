package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	
	public static final String SERVICE_KEY = "RoR%2BBwyqgR9cOhCexZ2BgDk8RmLjFXU3%2BOGt1N7qYIexNoqyouYTUuYKS4rxqOkL1GKC3Zmyd5PyIqQUapeZJQ%3D%3D";

	@ResponseBody
	@RequestMapping(value="air", produces="application/json; charset=UTF-8")
	public String getAirInfo(String location) throws IOException {
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&returnType=json"; // 리턴값은 기본이 xml이니까 json으로 하고 싶어서 넣어줌
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8"); //요청시 전달값에 한글이 있으면 인코딩 작업을 해줘야한다.

		URL requestURL = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
		urlConnection.setRequestMethod("GET"); // 안해줘도 기본값 있으나 연습으로 해봄
		
		BufferedReader br =	new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine()) != null) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
		
		return responseText;
		
	}
	
	
//	// 같은 url이지만 다른 것을 처리
//	@PostMapping(value="air") //  변경할거면 post 매핑
//	public String getAirInfo() {
//		
//	}
//	
//	@GetMapping(value="air") // 자원을 가져오려면 get 매핑
//	public String getAirInfo() {
//		
//	}

}
