package com.kh.spring.common.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CookieController {

	/*
	 * cookie
	 * 브라우저에 저장되는 데이터 조각, 주로 사용자를 식별하고 상태정보를 기억하는데 사용
	 * 쿠키는 클라이언트(브라우저)의 로컬 저장소에 저장이 된다.
	 * 저장된 쿠키정보는 서버에 http요청 시 헤더에 담겨 함께 전송이 된다.
	 * 
	 * 쿠키는 보안성이 낮고 개인정보 유출에 취약할 수 있다.
	 * 따라서 중요한 정보를 저장하는데 사용하려면 보안적인 조취가 필요
	 * 
	 */
	
	// 쿠키는 url에 저장된다.
	
	//	이런 쿠키 너가 저장해!  -> 브라우저에 저장함
	@RequestMapping("create")
	public String create(HttpServletResponse response) {
		// 쿠키는 객체를 생성한 다음 응답정보에 첨부할 수 있다.
		// name, value 속성을 필수로 작성해야 한다.
		
		Cookie ck = new Cookie("test", "hoingildong");
		ck.setMaxAge(60 * 60 * 24); // 시간설정하면 쿠키를 설정한 시간 동안 사용 가능: 여기서는 하루
		response.addCookie(ck);
		
		Cookie ck2 = new Cookie("test2", "hoingildong");
		response.addCookie(ck2); // 도구에 애플리케이션에 뜸
		
		Cookie ck3 = new Cookie("id", "hoingildong");
		response.addCookie(ck3);
		
		return "cookie/create";
	}
	
	
	@RequestMapping("delete")
	public String delete(HttpServletResponse response) {
		// 쿠키는 삭제 명령어가 따로 없음
		// 0초로 만료시간을 저정 수 덮어쓰기를 진행하면 된다.
		
		Cookie ck = new Cookie("test", "hoingildong");
		ck.setMaxAge(0); //키가 test인 것만 없앰
		response.addCookie(ck);

		
		return "cookie/delete";
	}
	
}
