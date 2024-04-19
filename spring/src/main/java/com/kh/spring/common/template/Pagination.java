package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {
	
	/*
	 * int listCount; // 현재 총 게시글 수
	 * int currnetPage; //현재 페이지(사용자가 요청한 페이지 = 사용자가 몇페이지인지 준다(프론트에서 보냄))
	 * int pageLimit; // 페이지 하단의 페이징 바 갯수
	 * int boardLimit; // 한 페이지 내에 보여질 게시글 최대 갯수
	 * 
	 *  값을 전달받아 maxPage, startPage, endPage 값을 구하고
	 *  하나의 PageInfo 객체로 만들어서 반환
	 */
	
	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		int maxPage = (int)Math.ceil((double)listCount / boardLimit); // 가장 마지막 페이지(총 페이지 수)
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1; // 페이징바의 시작
		int endPage = startPage + pageLimit -1; // 페이징바의 끝 수
		
		endPage = endPage > maxPage ? maxPage : endPage;
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		return pi;
	}
	
	

}
