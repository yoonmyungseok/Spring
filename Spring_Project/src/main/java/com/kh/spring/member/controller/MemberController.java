package com.kh.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 bean으로 등록됨
public class MemberController {
    /*
     * 스프링에서는 Controller를 일반 자바 클래스로 만드는 대신 @Controller라는 어노테이션을 붙여서
     * 컨트롤러임을 명시해준다.
     * 
     * 컨트롤러 클래스 내부에는 메소드를 만들어서 요청에 대한 처리내용을 작성한다.
     * - 메소드는 무조건 public으로 구현
     * - 반환값은 무조건 String 타입으로 지정(응답페이지의 파일명)
     * - 매개변수는 있어도 되고 없어도 된다(매개변수가 있을 경우 name 속성값과 매개변수명을 일치시킨다)
     * - 메소드명은 마음대로 의미부여해서 지어줄것
     * - 메소드마다 url 매핑값을 지정할 어노테이션을 붙여줘야 함
     */

    /*
     * @RequestMapping(value = "login.me")
     * public String loginMember(String userId, String userPwd) {
     * // 요청시 처리할 내용
     * System.out.println(userId);
     * System.out.println(userPwd);
     * 
     * // return "/WEB-INF/views/main.jsp";
     * // 항상 경로명은 "/WEB-INF/views/~~/~~.jsp"
     * // 이 틀에 맞게 작성해야 하기 때문에
     * // DispatcherServlet의 설정파일인 servlet-context.xml파일에서
     * // view resolver 설정을 참조해서 응답페이지명을 지정해야한다.
     * // return "main"; //포워딩 방식으로 응답
     * return "redirect:/"; // sendRedirect 방식으로 응답
     * }
     */

}
