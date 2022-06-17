package com.kh.ajax;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 컨트롤러 역할의 bean 등록
public class AjaxController {

    // 1. HttpServletResponse 객체로 응답데이터 응답하기(기존의 jsp/servlet에서 썼던 방식)
    /*
     * @RequestMapping("ajax1.do")
     * public void ajaxMethod1(String name, int age, HttpServletResponse response)
     * throws IOException {
     * // System.out.println(name);
     * // System.out.println(age);
     * 
     * // 원래의 흐름
     * // Service->Dao->Mapper->DB
     * 
     * // 요청 처리가 다 되었다라는 가정 하에
     * // 요청한 페이지로 "응답한 데이터"를 보내기
     * 
     * String responseData = "응답문자열: " + name + "님은 " + age + "살 입니다.";
     * 
     * response.setContentType("text/html; charset=utf-8");
     * response.getWriter().print(responseData);
     * // Servlet 때에는 doGet() 메소드 정의 부분에 throws 구문이 자동완성 되어있었지만
     * // Spring에서는 내가 직접 요청시 처리할 Controller 메소드를 만들어야 하기 때문에
     * // 예외가 발생할법한 구문은 try~catch 블럭으로 직접 처리하거나 아니면
     * // DispatcherServlet이 예외를 처리할 수 있게끔 throws 구문을 붙인다.
     * 
     * }
     */

    // 2.응답할 데이터를 문자열로 반환(HttpServletResponse 객체를 안쓸 수 있음)
    @ResponseBody
    @RequestMapping(value = "ajax1.do", produces = "text/html; charset=utf-8")
    public String ajaxMethod1(String name, int age) {
        String responseData = "응답문자열: " + name + "님은" + age + "살 입니다.";
        return responseData;
        // 404 오류 발생: /WEB-INF/views/~~~~.jsp 로 응답페이지가 지정되어있기 때문에 오류
        // =>만약에 응답 데이터로써 String 타입의 값을 리턴하고 싶다면
        // 내가 리턴할 값이 응답페이지와 관련된 내용이 아니라 응답데이터임을 명시해줘야 함
        // @ResponseBody 어노테이션을 이용
        // 단, 한글일 경우에는 @RequestMapping 어노테이션에 produces 속성으로 인코딩 설정도 해줘야함
    }
}
