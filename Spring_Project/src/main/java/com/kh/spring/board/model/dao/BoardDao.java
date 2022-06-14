package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

@Repository
public class BoardDao {

    public int selectListCount(SqlSessionTemplate sqlSession) {
        return sqlSession.selectOne("boardMapper.selectListCount");
    }

    public ArrayList<Board> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
        int limit = pi.getBoardLimit();
        int offset = (pi.getCurrentPage() - 1) * limit;

        RowBounds rowBounds = new RowBounds(offset, limit);
        return (ArrayList) sqlSession.selectList("boardMapper.selectList", null, rowBounds);
    }

}
