package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardDao boardDao;

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public int selectListCount() {
        return boardDao.selectListCount(sqlSession);
    }

    @Override
    public ArrayList<Board> selecList(PageInfo pi) {
        return boardDao.selectList(sqlSession, pi);
    }

    @Override
    public int insertBoard(Board b) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int increaseCount(int boardNo) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Board selectBoard(int boardNo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int deleteBoard(int boardNo) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateBoard(Board b) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<Reply> selectReplyList(int boardNo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int insertReply(Reply r) {
        // TODO Auto-generated method stub
        return 0;
    }

}
