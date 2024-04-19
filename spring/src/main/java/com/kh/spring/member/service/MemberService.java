package com.kh.spring.member.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	// 로그인 서비스
	Member loginMember(Member m);
	
	// 회원가입: id check 위한 서비스
	int idCheck(String checkId);
	
	// *** 실제 회사에서는 예상할 수 있는 서비스들을 안터페이스에 짜놓고 만들어서 구현한다. ***
	
	// 회원가입
	int insertMember(Member m);
	
	// 회원수정
	int updateMember(Member m);
	
	// 회원탈퇴
	int deleteMember(String userId);

}
