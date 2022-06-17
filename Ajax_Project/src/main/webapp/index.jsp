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
                    $("#result1").text(result);
                },
                error: function () {
                    console.log("ajax 통신 실패!");
                }
            });
        }
    </script>
</body>

</html>