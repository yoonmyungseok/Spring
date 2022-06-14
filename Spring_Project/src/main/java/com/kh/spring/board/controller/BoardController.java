package com.kh.spring.board.controller;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 메뉴의 "자유게시판" 메뉴를 클릭해서 요청한 경우=> /list.bo(기본적으로 1번 페이지르 요청하게끔)
    // 페이징바의 "숫자"를 클릭해서 요청한 경우=>/list.bo?cpage=요청하는페이지수 )
    @RequestMapping("list.bo")
    public String selectList(@RequestParam(value = "cpage", defaultValue = "1") int currentPage, Model model) {
        // System.out.println("cpage:" + currentPage);

        // 페이징처리를 위한 변수들 셋팅=>PageInfo 객체

        int listCount = boardService.selectListCount();

        int pageLimit = 10;
        int boardLimit = 5;

        PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        ArrayList<Board> list = boardService.selecList(pi);

        model.addAttribute("pi", pi);
        model.addAttribute("list", list);
        // 게시판 리스트 화면 포워딩
        return "board/boardListView";
    }

}
