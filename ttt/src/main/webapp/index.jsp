<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <h1>지진해일대피소 정보</h1>
    <button id="gogo">실행</button><br>
    <table id="result" border="1">
        <thead>
            <tr>
                <th>일련번호</th>
                <th>시도명</th>
                <th>시군구명</th>
                <th>대피장소명</th>
                <th>주소</th>
                <th>수용가능인원(명)</th>
                <th>해변으로부터의거리</th>
                <th>대피소분류명</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>

    <script>
        $(function(){
            $("#gogo").click(function(){
                $.ajax({
                    url: "disaster.do",
                    success: function (result) {
                        let itemArr=result.TsunamiShelter[1].row;
                        let value="";

                        for(let i=0; i<itemArr.length; i++){
                            let item=itemArr[i];
                            value+="<tr>"
                                +"<td>"+item.id+"</td>"
                                +"<td>"+item.sido_name+"</td>"
                                +"<td>"+item.sigungu_name+"</td>"
                                + "<td>"+item.shel_nm+"</td>"
                                + "<td>"+item.address+"</td>"
                                + "<td>"+item.shel_av+"</td>"
                                + "<td>"+item.lenth+"</td>"
                                + "<td>"+item.shel_div_type+"</td>"
                                +"</tr>";
                        }
                        
                        $("#result>tbody").html(value);
                    },
                    error:function(){
                        console.log("ajax 통신 실패!");
                    }
                });
            })
        })
    </script>
</body>
</html>