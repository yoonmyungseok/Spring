package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 bean으로 등록됨
public class MemberController {
    // private MemberService memberService = new MemberServiceImpl();
    /*
     * 기존 객체 생성 방식
     * 객체 간의 결합도가 높아짐(소스코드의 수정이 일어났을 경우 일일이 다 바꿔줘야 한다)
     * 서비스가 동시에 많은 횟수로 요청될 경우 그만큼 객체가 많이 생성된다.
     */

    @Autowired
    private MemberService memberService;
    /*
     * Spring DI(Dependency Injection)을 이용한 방식
     * 객체를 생성해서 주입해줌(객체 간의 결합도를 낮춰줌)
     * new 라는 키워드 없이 선언문만 써줘도되지만 @Autowired라는 어노테이션을 받드시 작성해야 함
     */

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    /*
     * *Spring에서 요청시 전달값을 뽑는 방법
     * 1. HttpServletRequest 객체를 이용하여 전달받기(기존의 jsp/servlet 방식)
     * 
     * 해당 메소드의 매개변수로 HttpServletRequest 객체를 작성해 두면
     * DispatcherServlet에 의해서 해당 메소드가 호출될 시점에
     * 스프링 컨테이너에 의해서 request 객체가 생성된 후에 매개변수로 주입해줌
     * 
     */
    /*
     * @RequestMapping(value = "login.me") // RequestMapping 어노테이션을 붙여줌으로써 컨트롤러로 등록
     * // login.me 요청이 들어왔을 때 DispatcherServlet에 의해
     * // loginMember 메소드가 호출됨
     * public void loginMember(HttpServletRequest request) {
     * String userId = request.getParameter("userId");
     * String userPwd = request.getParameter("userPwd");
     * 
     * System.out.println("userId:" + userId);
     * System.out.println("userPwd:" + userPwd);
     * }
     */

    /*
     * 2. @RequestParam 어노테이션을 이용하는 방법
     * request.getParameter("키")의 역할을 대신해주는 어노테이션
     * value 속성으로 jsp에서 작성했던 name 속성값을 담으면 알아서 해당 매개변수로 받아올 수 있다.
     * 만약, 넘어온 값이 비어있는 상태라면 defaultValue 속성으로 기본값을 지정해줄 수 있다.
     */
    /*
     * @RequestMapping(value = "login.me")
     * public void loginMember(
     * 
     * @RequestParam(value = "userId", defaultValue = "aaa") String userId,
     * 
     * @RequestParam(value = "userPwd") String userPwd) {
     * System.out.println("userId: " + userId);
     * System.out.println("userPwd:" + userPwd);
     * }
     */

    /*
     * 3. @RequestParam 어노테이션을 생략하는 방법
     * 단, 매개변수명을 jsp의 name 속성값과 동일하게 셋팅해둬야 자동으로 값이 주입됨
     * 한가지 단점이라고 한다면 위에서 썼던 defaultValue 같은 추가적인 속성을 사용할 수 없음
     * 
     */
    /*
     * @RequestMapping(value = "login.me")
     * public void loginMember(String userId, String userPwd) {
     * System.out.println("userId: " + userId);
     * System.out.println("userPwd: " + userPwd);
     * 
     * Member m = new Member();
     * m.setUserId(userId);
     * m.setUserPwd(userPwd);
     * 
     * //Service쪽 메소드에 m을 전달하면서 조회
     * }
     */

    /*
     * 4.커맨드 객체 방식
     * 해당 메소드의 매개변수로
     * 요청 시 전달값을 담고자 하는 VO클래스 타입을 셋팅 후
     * jsp의 name 속성값을 VO 클래스에 담고자하는 필드명으로 작성
     * 
     * 스프링 컨테이너가 해당 객체를 내부적으로 기본생성자를 이용해서 만들고
     * 추가적으로 해당 값에 해당하는 setter 메소드들을 호출해서 가공해서 매개변수로 주입해줌
     * 
     * 반드시 name 속성값과 해당 객체의 필드명이 일치해야함
     */
    /*
     * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 url 재요청
     * 
     * 1.스프링에서 제공하는 Model 객체(==requestScope 객체)를 사용하는 방법
     * 포워딩할 응답뷰로 전달하고자 하는 데이터를
     * 맵 형식 (key-value)으로 담을 수 있는 객체
     * Model 객체는 requestScope이다.
     * 
     * 화면을 응답하고자 할 경우 return "문자열"; 로 표현
     * [표현법]
     * model.addAttribute("키", 밸류);
     * 
     */
    /*
     * @RequestMapping(value = "login.me")
     * public String loginMember(Member m, Model model, HttpSession session) {
     * // System.out.println("userId:" + m.getUserId());
     * // System.out.println("userPwd:" + m.getUserPwd());
     * 
     * // Service 쪽으로 호출하면서 m을 넘기기
     * Member loginUser = memberService.loginMember(m);
     * 
     * // 결과에 따른 응답뷰 지정
     * if (loginUser == null) {
     * // 로그인 실패=>에러 문구를 requestScope에 담아 에러페이지 포워딩
     * // 기존 방법
     * // request.setAttribue("errorMsg", "로그인 실패");
     * 
     * // Model 객체를 이용한 방법
     * model.addAttribute("errorMsg", "로그인 실패");
     * 
     * // 에러페이지의 경로:webapp/WEB-INF/views/common/errorPage.jsp
     * // 상대경로: WEB-INF/views/common/errorPage.jsp
     * 
     * // 기존 방법
     * //
     * reqeust.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(
     * request,
     * // response);
     * 
     * // servlet-context.xml 파일에 View Resolver 설정에서 확인 가능
     * // prefix+내가작성한주소+suffix
     * // "/WEB-INF/views/"+내가작성한주소+".jsp"
     * return "common/errorPage";
     * 
     * } else {
     * // 로그인 성공=>loginUser 변수를 sessionScope에 담고 메인페이지로 url 재요청
     * 
     * // sessionScope에 로그인한 회원의 정보 담기
     * // 기존 방식으로 session 객체 생성:request.getSession();
     * 
     * // 스프링에서는 내가 session을 이용하고자 할 때
     * // 메소드의 매개변수로 정의해두면 알아서 스프링 컨테이너에 의해서 객체 생성 후 주입됨
     * session.setAttribute("loginUser", loginUser);
     * 
     * 
     * //기존의 포워딩 방식: return "파일경로";
     * //기존의 url 재요청방식: return "redirect:요청할url";
     * 
     * 
     * // 기존방식
     * // response.sendRedirect(request.getContextPath());
     * 
     * return "redirect:/"; // /(슬래쉬): contextPath뒤에 붙는 /(슬래쉬)
     * }
     * }
     */
    /*
     * 2.스프링에서 제공하는 ModelAndView 객체를 사용하는 방법
     * Model은 데이터를 단순히 key-value 세트로 담는 역할이라고 한다면
     * View는 응답뷰에 대한 정보를 담을 수 있는 역할
     * Model과 View가 합쳐진 객체가 ModelAndView 객체임
     * 
     * 이 경우에는 return 타입이 String이 아닌 ModelAndView 타입이여야 한다.
     * 추가적으로, Model 객체는 단독 존재가 가능(1번 방법으로 응답뷰를 지정 가능), View는 단독 존재가 불가능
     * 
     * [표현법]
     * model에 key-value 세트로 데이터를 담을 경우: mv.addObject("키", 밸류);
     * view에 응답 뷰의 정보를 담을 경우: mv.setViewName("포워딩할 jsp파일경로 또는 redirect:요청할url");
     */
    @RequestMapping(value = "login.me")
    public ModelAndView loginMember(Member m, ModelAndView mv, HttpSession session) {
        // 비밀번호 암호화 후
        // m의 userPwd 필드: 평문 비밀번호 값
        // loginUser의 userPwd 필드: 암호화된 비밀번호 값

        Member loginUser = memberService.loginMember(m);
        // 아이디만 일치하는지 1차 체크

        // 비밀번호도 일치하는지 2차 체크
        // BCryptPasswordEncoder 클래스에서 제공하는 matches 메소드
        // matches(평문, 암호문)을 작성하면 내부적으로 대조 작업이 이루어져 두 구문이 일치하는지 비교
        // 일치한다면 true/일치하지 않으면 false

        // 일단 조회된 회원결과가 있는지 그리고 평문비밀번호와 암호문 비밀번호가 일치하는지
        if (loginUser != null && bCryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
            // 로그인 성공
            session.setAttribute("loginUser", loginUser);
            session.setAttribute("alertMsg", "로그인에 성공했습니다");

            mv.setViewName("redirect:/");
        } else {
            // 로그인 실패=>에러페이지로 포워딩
            mv.addObject("errorMsg", "로그인 실패");
            mv.setViewName("common/errorPage");
        }

        // 비밀번호 암호화 전
        /*
         * if (loginUser == null) {
         * // 실패=>에러문구를 requestScope에 담아서 에러페이지로 포워딩
         * 
         * // 1번 방식
         * // model.addAttribute("errorMsg","로그인 실패");
         * 
         * // ModelAndView 객체 사용할 경우
         * mv.addObject("errorMsg", "로그인 실패");
         * 
         * // 1번방식
         * // return "common/errorPage";
         * 
         * // ModelAndView 객체 사용할 경우
         * mv.setViewName("common/errorPage");
         * 
         * } else {
         * // 성공=>sessionScope에 회원정보를 담고 메인페이지로 url 재요청
         * session.setAttribute("loginUser", loginUser);
         * 
         * // 1번 방식
         * // return "redirect:/";
         * 
         * // ModelAndView 객체를사용할 경우
         * mv.setViewName("redirect:/"); // 메인페이지로 url 재요청
         * 
         * }
         */
        return mv;
    }

    // @RequestMapping(value = "logout.me")
    @RequestMapping("logout.me") // url매핑값만 적을 경우 value 속성 생략 가능
    public String logoutMember(HttpSession session) {
        // session을 무효화: session().invalidate();
        session.invalidate();

        // 메인페이지로 url 재요청
        return "redirect:/";
    }

    @RequestMapping("enrollForm.me")
    public String enrollForm() {
        // 단순히 회원가입 폼을 띄워주는 역할
        // /WEB-INF/views/member/memberEnrollForm.jsp 로 포워딩

        return "member/memberEnrollForm";
    }

    @RequestMapping("insert.me")
    public String insertMember(Member m, Model model, HttpSession session) {
        // System.out.println(m);
        // 1.한글 깨지는 이슈
        // 한글이 깨지는 이슈 발생=>post 방식일 경우 인코딩 설정을 먼저 해야함
        // 인코딩 필터를 적용하러 web.xml(배포서술자)에 스프링에서 제공하는 인코딩 필터를 등록

        // 2.선택 입력사항을 적지 않고 요청을 보냈을 때 400 에러(Bad Request Error) 발생
        // =>어디서? age 필드는 int 형인데 만약 값을 일력하지 않은 경우 null 값이 들어오기 때문
        // =>해결방법:Member 클래스의 age 필드를 String 타입으로 변경

        // 3.비밀번호가 사용자가 입력한 있는 그대로의 "평문(숨겨지지 않은 상태)"
        // =>암호화 작업을 통해서 "암호문"으로 변경 후 DB에 저장
        // =>Bcrypt 방식 (스프링 시큐리티 모듈에서 제공)

        // 1)스프링 시큐리티 모듈에서 제공하는 암호화 라이브러리 추가(pom.xml에 추가)
        // 2)BCryptPasswordEncoder 클래스를 xml 파일에 bean 등록
        // =>보통은 bean 들록시 root-context.xml 파일이나 servlet-context.xml 파일에 등록하지만,
        // 보통 암호화 관련된 bean 설정은 별도로 빼두는 경우가 많음
        // =>spring/spring-security.xml 파일 생성
        // =>bean 등록
        // =>web.xml에 spring-security.xml 파일 읽히도록 설정
        // 3)@Autowired를 이용해서 언제든지 bcryptPasswordEncoder 객체 사용가능

        // "평문" --> "암호문"
        // System.out.println("평문: " + m.getUserPwd());

        // 암호화 작업(암호문을 만들어 내는 과정)=>bcryptPasswordEncoder.encode() 메소드
        String encPwd = bCryptPasswordEncoder.encode(m.getUserPwd());
        // System.out.println("암호문: " + encPwd);

        // m=>userPwd 필드값을 encPwd 으로 바꿔치기 후 insert 요청
        m.setUserPwd(encPwd);

        int result = memberService.insertMember(m);

        if (result > 0) {
            // 성공=>메인페이지 url 재요청
            // 1회성 알람 메시지 실어보내기
            session.setAttribute("alertMsg", "성공적으로 회원가입이 되었습니다");
            return "redirect:/";
        } else {
            // 실패=>에러문구를 담아서 에러페이지로 포워딩
            model.addAttribute("errorMsg", "회원가입 실패");
            return "common/errorPage";
        }
    }
}
