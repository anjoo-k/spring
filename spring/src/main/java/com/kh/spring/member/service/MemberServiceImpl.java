package com.kh.spring.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 메서드 대신 만들어줌(memcontroller 참고)
@Service // Component 보다 더 구체화해서 Service bean으로 등록시키는 것 
public class MemberServiceImpl implements MemberService {
	
	
	private final SqlSessionTemplate sqlSession;
	//기존의 myBatis sqlSession 객체 대체
	// sqlSessionTemplate bean 등록 후 @Autowired 헀음

	private final MemberDao memberDao;
	// 객체생성하지 않고 주입받는다(스프링의 의존성)
	
	@Override
	public Member loginMember(Member m) {
		// 스프링이 스프링컨테이너에서 사용 후 자동으로 반납시켜주기 때문에 close 메소드로 자원반납할 필요 없음
		// 한줄로 기술이 가능
		return memberDao.loginMember(sqlSession, m);
	}

	@Override
	public int idCheck(String checkId) {
		return memberDao.idCheck(sqlSession, checkId);
	}

	@Override
	public int insertMember(Member m) {
		return memberDao.insertMember(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		return memberDao.updateMember(sqlSession, m);
	}

	@Override
	public int deleteMember(String userId) {
		return memberDao.deleteMember(sqlSession, userId);

	}

}
