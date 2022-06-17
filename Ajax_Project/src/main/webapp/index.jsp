<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <title>title</title>
</head>

<body>
    <h1>Spring에서의 AJAX 사용법</h1>
    <h3>1.요청 시 전달값을 보내고, 응답 결과를 받아오기</h3>

    이름: <input type="text" id="name"><br>
    나이: <input type="number" id="age"><br>
    <button onclick="test1();">전송</button><br>
    <div id="result1"></div>

    <script>
        function test1() {
            $.ajax({
                url: "ajax1.do",
                data: {
                    name: $("#name").val(),
                    age: $("#age").val()
                },
                success: function (result) {
                    //console.log(result);
                    //$("#result1").text(result);
                    //JSONArray를 이용하여 여러개의 응답데이터 넘겨받기
                    /*
                    var resultStr="이름: "+result[0]+"<br>"+"나이: "+result[1];
                    $("#result1").html(resultStr);
                    */
                    
                    //JSONObject를 이용하여 여러개의 응답데이터 넘겨받기
                    //=>속성에 접근: 객체명.속성명 또는 객체명["속성명"]
                    var resultStr="이름: "+result.name+"<br>"+"나이: "+result.age;
                    $("#result1").html(resultStr);
                    
                },
                error: function () {
                    console.log("ajax 통신 실패!");
                }
            });
        }
    </script>
    <hr>
    <h3>2. 조회요청 후 조회된 한 회원의 객체를 응답 받아서 출력해보기</h3>
    조회할 회원번호: <input type="number"  id="userNo"><br>
    <button id="btn">조회</button>
    <div id="result2"></div>
    <script>
    	$(function(){
    		$("#btn").click(function(){
    			$.ajax({
    				url:"ajax2.do",
    				data:{userNo:$("#userNo").val()},
    				success:function(result){
    					var resultStr=  "<ul>"
                                    +   "<li>이름: "+result.userName+"</li>"
                                    +   "<li>아이디: "+result.userId+"</li>"
                                    +   "<li>비밀번호: "+result.userPwd+"</li>"
                                    +   "<li>나이: "+result.age+"</li>"
                                    +   "<li>휴대폰: "+result.phone+"</li>"
                                    +   "</ul>";
                    $("#result2").html(resultStr);
                                    
    				},
    				error:function(){
    					console.log("ajax 통신 실패")
    				}
    			})
    		})
    	})
    </script>
    <hr>
    <h3>3.조회요청 후 조회된 회원리스트를 응답받아서 출력해보기</h3>
    <button onclick="test3();">회원 전체 조회</button>
    <br><br>
    <table border="1" id="result3">
        <thead>
            <tr>
                <th>아이디</th>
                <th>비밀번호</th>
                <th>이름</th>
                <th>나이</th>
                <th>전화번호</th>
            </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <script>
        function test3(){
            $.ajax({
                url:"ajax3.do",
                success:function(result){
                    //console.log(result);
                    var resultStr="";
                    for (const r of result) {
                        resultStr += "<tr>"
                            + "<td>" + r.userId + "</td>"
                            + "<td>" +r.userPwd + "</td>"
                            + "<td>" + r.userName + "</td>"
                            + "<td>" + r.age + "</td>"
                            + "<td>" + r.phone + "</td>"
                            + "</tr>";
                    }  
                    /*
                    for(var i=0; i<result.length; i++){
                        resultStr+= "<tr>"
                                +       "<td>"+result[i].userId+"</td>"
                                +       "<td>"+result[i].userPwd+"</td>"
                                +       "<td>"+result[i].userName+"</td>"
                                +       "<td>"+result[i].age+"</td>"
                                +       "<td>"+result[i].phone+"</td>"
                                +   "</tr>";
                    }*/
                    $("#result3>tbody").html(resultStr);
                },
                error:function(){
                    console.log("ajax 통신 실패");
                }
            })
        }
    </script>
</body>

</html>