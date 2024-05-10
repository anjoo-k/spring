package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Run {
	public static final String SERVICE_KEY = "RoR%2BBwyqgR9cOhCexZ2BgDk8RmLjFXU3%2BOGt1N7qYIexNoqyouYTUuYKS4rxqOkL1GKC3Zmyd5PyIqQUapeZJQ%3D%3D";

	public static void main(String[] args) throws IOException {
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&returnType=json"; // 리턴값은 기본이 xml이니까 json으로 하고 싶어서 넣어줌
		url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8"); //요청시 전달값에 한글이 있으면 인코딩 작업을 해줘야한다.
		
		
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
		System.out.println(responseText);

		
		// 각각의 item 정보 -> JSON 형식으로 변환
		// JSONObject, JSONArray -> json라이브러리에서 제공하는 객체들
		// JsonObject, JsonArray -> Gson에서 제공
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject(); //getasJsonObject가 우리가 평소에 쓰던 json
		System.out.println(totalObj);
		
		JsonObject responseObj = totalObj.getAsJsonObject("response"); //totalObj 안에 있는 키로 object 꺼내올 수 있다
		JsonObject bodyObj = responseObj.getAsJsonObject("body");
		System.out.println(bodyObj);
		
		int totalCount = bodyObj.get("totalCount").getAsInt();
		JsonArray itemArr = bodyObj.getAsJsonArray("items");
		System.out.println(itemArr);
		
		JsonObject jOnj1 = itemArr.get(0).getAsJsonObject(); // 0번째에 담긴 구의 미세먼지 정보를 가진 json 객체
		System.out.println(itemArr);
		
		for(int i = 0; i < itemArr.size(); i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject();// i번째에 담긴 구의 미세먼지 정보를 가진 json 객체
			System.out.println("측정소명: " + item.get("stationName").getAsString());
			System.out.println("측정일시: " + item.get("dataTime").getAsString());
			System.out.println("미세먼지농도: " + item.get("pm10Value").getAsString());

		}
		
		br.close();
		urlConnection.disconnect();
	}
	
}
