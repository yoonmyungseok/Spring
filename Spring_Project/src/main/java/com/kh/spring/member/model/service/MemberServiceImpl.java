package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//@Component  //bean으로 등록시키겠다 
@Service // Service 역할을 하는 bean으로 등록시키겠다
public class MemberServiceImpl implements MemberService {

    // 기존방식
    // MemberDao memberDao = new MemberDao();

    // DI를 이용한 방식
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public Member loginMember(Member m) {
        // 기존에 순수 마이바티스 때에는 SqlSession 객체 생성
        // 스프링에 마이바티스를 붙여 쓸 때는 SqlSessionTemplate 객체 생성=>xml 방식으로 bean 등록 해둠

        // Dao에 만들어진 sqlSession과 전달받은 값을 넘김

        return memberDao.loginMember(sqlSession, m);

        // SqlSessionTemplate 객체를 bean으로 등록해주고 @Autowired 해줬기 때문에
        // 스프링 컨테이너가 사용 후 알아서 객체를 반납까지 해줌
        // =>우리가 직접 close 메소드를 호출할 필요가 없어짐

    }

    @Override
    public int insertMember(Member m) {
        // int result = memberDao.insertMember(sqlSession, m);

        // SqlSessionTemplate 객체가 자동으로 커밋해줌
        // return result;
        return memberDao.insertMember(sqlSession, m);

    }

    @Override
    public int updateMember(Member m) {
        return 0;
    }

    @Override
    public int deleteMember(String userId) {
        return 0;
    }

    @Override
    public int idCheck(String checkId) {
        return 0;
    }

}
