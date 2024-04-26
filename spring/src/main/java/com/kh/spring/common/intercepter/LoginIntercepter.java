package com.kh.spring.common.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginIntercepter extends HandlerInterceptorAdapter {
	
	/*
	 * HandlerInterceptor
	 * 
	 * -- Controller가 실행되기 전/후에 낚아채서 실행된다.
	 * -- 로그인 유/무  판단, 회원권한체크
	 * 
	 * preHandle(전처리) : DispatcherServlet이 컨트롤러를 호출하기 전에 낚아채는 영역
	 * postHandle(후처리) : 컨트롤러에서 요청 후 DispatcherServlet으로 view 정보를 리턴할 때 낚아채는 영역
	 * 
	 */
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// return : true => 기존요청 흐름대로 진행(Controller로 이동)
		// return : false => 요청 중단 후 반환
		
		// 매핑을 해줘야 특정한 요청에 관해서만 적용이 가능하다.

		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") != null) {
			return true;
		} else {
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	

	

}
