package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 메뉴바의 "자유게시판" 메뉴를 클릭해서 요청한 경우 => /list.bo (기본적으로 1 번 페이지를 요청하게끔 처리)
    // 페이징바의 "숫자" 를 클릭해서 요청한 경우 => /list.bo?cpage=요청하는페이지수
    @RequestMapping("list.bo")
    public String selectList(
            @RequestParam(value = "cpage", defaultValue = "1") int currentPage,
            Model model) {

        // System.out.println("cpage : " + currentPage);

        // 페이징처리를 위한 변수들 셋팅 => PageInfo 객체

        int listCount = boardService.selectListCount();

        int pageLimit = 10;
        int boardLimit = 5;

        PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        ArrayList<Board> list = boardService.selectList(pi);

        // model.addAttribute("pi", pi);
        model.addAttribute("list", list).addAttribute("pi", pi);

        // 게시판 리스트 화면 포워딩
        return "board/boardListView"; // /WEB-INF/views/board/boardListView.jsp
    }

    @RequestMapping("enrollForm.bo")
    public String enrollForm() {

        // 단순히 게시글 작성 폼을 띄워주는 역할
        return "board/boardEnrollForm"; // /WEB-INF/views/board/boardEnrollForm.jsp
    }

    /*
     * * 만약 다중파일을 업로드 하고자 한다면
     * 1. jsp 에 여러개의 input type="file" 요소를 만듬
     * 2. 1번에서 만들어진 input type="file" 요소들에 name 속성값을 동일하게 부여 (ex. upfile)
     * 3. Controller 의 메소드의 매개변수
     * MultipartFile[] upfile 로 정의 => [index] 접근
     * 또는
     * List<MultipartFile> upfile 로 정의 => .get(index) 접근
     * => 0번 인덱스에서부터 파일들이 차곡차곡 담겨서 넘어옴!!
     */
    @RequestMapping("insert.bo")
    public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {

        System.out.println(b);
        System.out.println(upfile);
        // 파일업로드 관련한 jar 파일들 추가 안한상태, bean 등록도 아직 안한상태
        // => null 값이 뜸

        // 전달된 파일이 있을 경우 => filename 속성에 파일명이 있음 (원본명)
        // 전달된 파일이 없을 경우 => filename 속성값이 비어있음
        // => 첨부파일을 선택했든 안했든 간에 MultipartFile 객체는 생성된 객체임 (null 값이 아님)

        // 전달된 파일이 있을 경우
        // 1. 파일명 수정 => yyyymmddhhmmssxxxxx.확장자
        // 2. 서버로 업로드
        // 3. 원본명, 서버에 업로드된 수정명, 경로를 db 로 insert
        if (!upfile.getOriginalFilename().equals("")) {

            /*
             * // 파일명 수정 후 업로드 시키기
             * // 예) "flower.png" => "2022061511020112345.png"
             * // 1. 원본파일명 가져오기
             * String originName = upfile.getOriginalFilename(); // "flower.png"
             * 
             * // 2. 시간 형식을 문자열로 뽑아내기
             * // "20220615110201" (년월일시분초) => SimpleDateFormat
             * String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new
             * Date());
             * 
             * // 3. 뒤에 붙을 5자리 랜덤값 뽑기
             * // "13253"
             * int ranNum = (int)(Math.random() * 90000 + 10000);
             * 
             * // 4. 원본파일명으로부터 확장자 부분만 추출
             * // ".png"
             * String ext = originName.substring(originName.lastIndexOf("."));
             * 
             * // 5. 모두 이어 붙이기
             * String changeName = currentTime + ranNum + ext;
             * 
             * // 6. 업로드 하고자 하는 물리적인 경로 알아내기
             * String savePath =
             * session.getServletContext().getRealPath("/resources/uploadFiles/");
             * 
             * // 7. 경로와 수정파일명을 합체시킨 후 저장
             * try {
             * upfile.transferTo(new File(savePath + changeName));
             * } catch (IllegalStateException | IOException e) {
             * e.printStackTrace();
             * }
             */
            // saveFile 메소드로 위의 코드를 따로 정의함
            String changeName = saveFile(upfile, session);

            // 8. Board b 에 originName, changeName 필드에 값 저장
            b.setOriginName(upfile.getOriginalFilename());
            b.setChangeName("resources/uploadFiles/" + changeName);
        }

        // Service 단으로 b 를 넘겨서 insert 요청
        // 넘어온 첨부파일이 있을 경우 b : 제목, 내용, 작성자, 원본파일명, 수정파일명
        // 넘어온 첨부파일이 없을 경우 b : 제목, 내용, 작성자
        int result = boardService.insertBoard(b);

        if (result > 0) { // 성공 => 게시글 리스트페이지로 url 재요청

            session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");

            return "redirect:list.bo";
        } else { // 실패 => 에러페이지 포워딩

            model.addAttribute("errorMsg", "게시글 등록 실패");

            return "common/errorPage"; // /WEB-INF/views/common/errorPage.jsp
        }
    }

    @RequestMapping("detail.bo")
    public ModelAndView selectBoard(int bno, ModelAndView mv) {

        // bno 에는 상세조회하고자 하는 해당 게시글 번호가 담겨있음

        // 1. 해당 게시글 조회수 증가용 서비스 먼저 요청
        int result = boardService.increaseCount(bno);

        if (result > 0) {
            // 2. 조회수 증가가 잘 이루어졌다면 그때 상세조회 서비스 요청
            Board b = boardService.selectBoard(bno);

            // ModelAndView 객체의 addObject 메소드는 자기자신을 리턴해주기 때문에
            // 한번에 setViewName 메소드까지 메소드체이닝으로 호출 가능하다.
            mv.addObject("b", b).setViewName("board/boardDetailView"); // /WEB-INF/views/board/boardDetailView.jsp
        } else { // 조회수 증가가 실패했을 경우

            mv.addObject("errorMsg", "게시글 상세조회 실패").setViewName("common/errorPage"); // /WEB-INF/views/common/errorPage.jsp
        }

        return mv;
    }

    /*
     * * 어노테이션 추가 정리
     * 
     * @RequestMapping(value="url매핑값", method=RequestMethod.POST)
     * == @PostMapping("url매핑값")
     * 
     * @RequestMapping(value="url매핑값", method=RequestMethod.GET)
     * == @GetMapping("url매핑값")
     */

    // @RequestMapping("delete.bo") // get 방식이든 post 방식이든 delete.bo 로 들어오는 요청은 다
    // 받아쳐주겠다.
    // @RequestMapping(value="delete.bo", method=RequestMethod.POST) // delete.bo 로
    // 들어오는 요청 중에 post 방식만 허용하겠다.
    @PostMapping("delete.bo") // post 방식으로 들어오는 delete.bo 에 해당되는 요청을 처리하겠다. => Spring 4 버전부터 지원
    public String deleteBoard(int bno, Model model, String filePath, HttpSession session) {

        int result = boardService.deleteBoard(bno);

        if (result > 0) { // 성공

            // 첨부파일이 있었던 경우 => 파일 삭제
            if (!filePath.equals("")) {

                String realPath = session.getServletContext().getRealPath(filePath);
                new File(realPath).delete();
            }

            // 문구 담아서
            session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");

            // 게시판 리스트 페이지로 url 재요청
            return "redirect:list.bo";
        } else { // 실패 => 에러문구 담아서 에러페이지로 포워딩

            model.addAttribute("errorMsg", "게시글 삭제 실패");

            return "common/errorPage"; // /WEB-INF/views/common/errorPage.jsp
        }

    }

    @PostMapping("updateForm.bo")
    public String updateForm(int bno, Model model) {

        // 해당 게시글의 상세조회 요청
        Board b = boardService.selectBoard(bno);

        // Model 에 데이터 담기
        model.addAttribute("b", b);

        // 수정하기 페이지 포워딩
        return "board/boardUpdateForm"; // /WEB-INF/views/board/boardUpdateForm.jsp
    }

    @RequestMapping("update.bo")
    public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {

        // 넘어온 첨부파일이 없을 경우 : filename 속성값이 빈 문자열
        // 넘어온 첨부파일이 있을 경우 : filename 속성값에 원본파일명이 들어있음
        if (!reupfile.getOriginalFilename().equals("")) { // "새로운" 첨부파일이 있을 경우

            // 4. 새롭게 첨부된 파일이 O, 기존 첨부파일 O
            if (b.getOriginName() != null) { // 기존 첨부파일의 원본명이 있을 경우

                // 기존 첨부파일을 서버로부터 삭제 (수정명)
                String savePath = session.getServletContext().getRealPath(b.getChangeName());
                new File(savePath).delete();
            }

            // 이 시점에서 서버에 파일 업로드 가능
            String changeName = saveFile(reupfile, session);

            // b 에 새로 넘어온 첨부파일에 대한 원본명, 수정명을 필드값으로 수정
            b.setOriginName(reupfile.getOriginalFilename());
            b.setChangeName("resources/uploadFiles/" + changeName);
        }

        // 이 시점에서
        // 새로운 첨부파일이 있을 경우 b 의 원본명, 수정명 필드가 수정되어있을 것
        // 새로운 첨부파일이 없을 경우 b 의 원본명, 수정명 필드가 그대로 남아있을것

        // b 에 무조건 담겨있는 내용
        // boardTitle, boardNo, boardContent
        /*
         * 추가적으로 고려해야 하는 경우의 수
         * 1. 새롭게 첨부된 파일이 X, 기존 첨부파일 X
         * => originName : null, changeName : null
         * 
         * 2. 새롭게 첨부된 파일이 X, 기존 첨부파일 O
         * => originName : 기존첨부파일의원본명, changeName : 기존첨부파일의수정명
         * 
         * 3. 새롭게 첨부된 파일이 O, 기존 첨부파일 X
         * => originName : 새로운첨부파일의원본명, changeName : 새로운첨부파일의수정명
         * 
         * 4. 새롭게 첨부된 파일이 O, 기존 첨부파일 O
         * => originName : 새로운첨부파일의원본명, changeName : 새로운 첨부파일의수정명
         */

        int result = boardService.updateBoard(b);

        if (result > 0) { // 성공

            session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");

            // detail.bo?bno=x 으로 url 재요청
            return "redirect:detail.bo?bno=" + b.getBoardNo();
        } else { // 실패

            model.addAttribute("errorMsg", "게시글 수정 실패");

            return "common/errorPage"; // /WEB-INF/views/common/errorPage.jsp
        }
    }

    @ResponseBody
    @RequestMapping(value = "rlist.bo", produces = "application/json; charset=utf-8")
    public String ajaxSelectReplyList(int bno) {
        ArrayList<Reply> list = boardService.selectReplyList(bno);
        return new Gson().toJson(list);
    }

    @ResponseBody
    @RequestMapping(value = "rinsert.bo", produces = "text/html; charset=utf-8")
    public String ajaxInsertReply(Reply r) {
        // Ajax 또한 커맨드객체 방식으로 요청 시 전달값을 받을 수 있다.
        int result = boardService.insertReply(r);
        return (result > 0) ? "success" : "fail";
    }

    @ResponseBody
    @RequestMapping(value = "topList.bo", produces = "application/json; charset=utf-8")
    public String ajaxTopBoardList() {
        ArrayList<Board> list = boardService.selectTopBoardList();
        return new Gson().toJson(list);
    }

    /* 밑에는 일반 메소드임 */

    // * 스프링에서 반드시 요청을 처리하는 메소드만 Controller 에 들어가라는 법은 없다!!
    // 현재 넘어온 첨부파일의 이름을 수정 후 서버의 그 폴더에 저장하는 메소드
    // => url 요청을 처리하는 메소드가 아닌 일반 메소드로 만듬
    public String saveFile(MultipartFile upfile, HttpSession session) {

        // 파일명 수정 후 업로드 시키기
        // 예) "flower.png" => "2022061511020112345.png"
        // 1. 원본파일명 가져오기
        String originName = upfile.getOriginalFilename(); // "flower.png"

        // 2. 시간 형식을 문자열로 뽑아내기
        // "20220615110201" (년월일시분초) => SimpleDateFormat
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 3. 뒤에 붙을 5자리 랜덤값 뽑기
        // "13253"
        int ranNum = (int) (Math.random() * 90000 + 10000);

        // 4. 원본파일명으로부터 확장자 부분만 추출
        // ".png"
        String ext = originName.substring(originName.lastIndexOf("."));

        // 5. 모두 이어 붙이기
        String changeName = currentTime + ranNum + ext;

        // 6. 업로드 하고자 하는 물리적인 경로 알아내기
        String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");

        // 7. 경로와 수정파일명을 합체시킨 후 저장
        try {
            upfile.transferTo(new File(savePath + changeName));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return changeName;
    }

}
