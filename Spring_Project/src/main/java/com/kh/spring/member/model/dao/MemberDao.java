package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository // "저장소"(DB)와 직접적인 연결을 하는 DAO에 붙이는 어노테이션
public class MemberDao {
    public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
        return sqlSession.selectOne("memberMapper.loginMember", m);
    }
}
