package com.kh.ajax;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

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
	/*
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
    */
    
    // 응답 데이터가 여러 개일 경우=>기존의 response 객체를 이용하는 방법
    /*
    @RequestMapping("ajax1.do")
    public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
    	//요청처리가 다 되었다는 가정 하에 여러 개의 데이터 응답
    	
    	response.setContentType("text/html; charset=utf-8");
    	response.getWriter().print(name);
    	response.getWriter().print(age);	
    	
    	
    	//JSON(Javascript Object Notation) 형태로 담아서 응답
    	//JSONArray=>[값, 값, 값, ...] => 자바스크립트의 배열==자바에서의 ArrayList
    	//JSONObject=>{키:값, 키:값, ...}=> 자바스크립트의 객체==자바에서의 HashMap
    	
    	//1.JSONArray 이용
    	
    	JSONArray jArr=new JSONArray();
    	jArr.add(name); //["홍길동"]
    	jArr.add(age);	//["홍길동", 20]
    	
    	response.setContentType("application/json; charset=utf-8");
    	response.getWriter().print(jArr);
    	
    	
    	//2.JSONObject 이용
    	JSONObject jObj=new JSONObject();
    	
    	jObj.put("name", name);
    	jObj.put("age",age);
    	
    	response.setContentType("application/json; charset=utf-8");
    	response.getWriter().print(jObj);
    	
    }
    */
	
	//여러 개의 응답데이터 보내기=>Spring 방법(response 객체 사용 안함)
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		JSONObject jObj=new JSONObject(); //{}
		jObj.put("name",name); //{name:"홍길동"}
		jObj.put("age", age); //{name:"홍길동", age:20}
		
		return jObj.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=utf-8")
    public String ajaxMethod2(int userNo) {
        //원래의 흐름
        //Service->DAO->mapper->DB

        //조회했다라는 가정 하에 Member 객체를 넘기기
        Member m = new Member("user01", "pass01", "홍길동", 20, "01012345678");

        //1.JSONObject 형식으로 응답
        /*
        JSONObject jObj = new JSONObject();
        jObj.put("userId", m.getUserId());
        jObj.put("userPwd", m.getUserPwd());
        jObj.put("userName", m.getUserName());
        jObj.put("age", m.getAge());
        jObj.put("phone", m.getPhone());
        
        return jObj.toJSONString();
        */

        //2. GSON을 이용하여 응답
        //=>내부적으로 필드명이 키값으로 잡혀서 JSON 형식으로 넘겨주는 과정이 수행됨
        //Gson gson = new Gson();
        //return gson.toJson(m);
        return new Gson().toJson(m);

    }
    
    @ResponseBody
    @RequestMapping(value = "ajax3.do", produces = "application/json; charset=utf-8")
    public String ajaxMethod3() {
        //DB로 부터 전체 회원들의 정보를 조회했다라는 가정 하에 
        ArrayList<Member> list = new ArrayList<>();

        list.add(new Member("user01", "pass01", "홍길동", 20, "123"));
        list.add(new Member("user02", "pass02", "김말똥", 21, "456"));
        list.add(new Member("user03", "pass03", "박말똥", 22, "789"));
        list.add(new Member("user04", "pass04", "이말똥", 23, "321"));
        list.add(new Member("user05", "pass05", "최말똥", 24, "654"));

        //GSON=>[{},{},{}, ...]
        return new Gson().toJson(list);
    }
	
}
