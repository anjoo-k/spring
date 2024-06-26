package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.XML;
import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class concertAPItestRun {
	public static final String SERVICE_KEY = "d4772ba0c40045f184603b34c70fd40e";
			
	public static void main(String[] args) throws IOException {
		String a = "PF242664";
		String url = "http://www.kopis.or.kr/openApi/restful/pblprfr/" + a;
		url += "?service=" + SERVICE_KEY;
		url += "&newsql=Y";
		
		
//		System.out.println(url);
		
		// 자바 코드로 서버에서 서버로 요청을 보내야함
		// ** HttpURLConnection 객체를 이용해서 요청을 보내야
		
		// 1. 요청하고자 한s url을 전달하면서 java.net.URL 객체 생성
		URL requestURL = new URL(url);
		
		// 2. 만들어진 URL 객체를 가지고 GttpURLConnection 객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
		
		// 3. 요청에 필요한 Header 설정하기
		urlConnection.setRequestMethod("GET"); // 안해줘도 기본값 있으나 연습으로 해봄
		
		// 4. 해당 api 서버로 요청 보낸 후 입력데이터 읽어오기
		BufferedReader br =	new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		
		String responseText = "";
		String line;
		while((line = br.readLine()) != null) {
			responseText += line;
		}
		System.out.println("1: " + responseText);
		
		
		// xml to jason
		org.json.JSONObject xmltojsonObj = XML.toJSONObject(responseText);
		String jsonObj = xmltojsonObj.toString();
		
		
		// 각각의 item 정보 -> JSON 형식으로 변환
		// JSONObject, JSONArray -> json라이브러리에서 제공하는 객체들
		// JsonObject, JsonArray -> Gson에서 제공
		
		JsonObject totalObj = JsonParser.parseString(jsonObj).getAsJsonObject(); //getasJsonObject가 우리가 평소에 쓰던 json
		System.out.println("2: " + totalObj);
		
		JsonObject dbsObj = totalObj.getAsJsonObject("dbs"); //totalObj 안에 있는 키로 object 꺼내올 수 있다
		System.out.println("3: " + dbsObj);
		
		JsonObject dbObj = dbsObj.getAsJsonObject("db"); // {를 여는 것은 jsonObject {다음에 [있으면 array 시작
		System.out.println("4: " + dbObj);
		
		System.out.println(dbObj.get("prfpdfrom").toString());
		
		JsonObject relates = dbObj.getAsJsonObject("relates");
		System.out.println("5:" + relates);
		
		JsonObject relate = relates.getAsJsonObject("relate");
		System.out.println(relate);
		
		System.out.println(relate.get("relateurl").toString());
		
//		JsonArray dbArr = dbsObj.getAsJsonArray("db");
//		JsonObject jOnj1 = dbObj.get(0).getAsJsonObject(); // 0번째에 담긴 정보를 가진 json 객체
//		System.out.println("5: " + jOnj1);
//		
//		for(int i = 0; i < dbArr.size(); i++) {
//			JsonObject item = dbArr.get(i).getAsJsonObject();// i번째에 담긴 정보를 가진 json 객체
//			System.out.println("공연이름: " + item.get("prfnm").getAsString());
//			System.out.println("시작일: " + item.get("prfpdfrom").getAsString());
//			System.out.println("공연시설명: " + item.get("fcltynm").getAsString());

//		}
		
		br.close();
		urlConnection.disconnect();
	}
	
}
