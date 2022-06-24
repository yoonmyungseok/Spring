<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <h1>실시간 대기오염 정보</h1>
    지역:
    <select id="location">
        <option>서울</option>
        <option>부산</option>  
        <option>대전</option>
        <option>경기</option>
    </select>
    <button id="btn1">해당 지역 대기오염정보</button><br><br>
    <table id="result1" border="1" align="center" style="text-align: center;">
        <thead>
            <tr>
                <th>측정소명</th>
                <th>측정일시</th>
                <th>통합대기환경수치</th>
                <th>미세먼지농도</th>
                <th>일산화탄소농도</th>
                <th>이산화질소농도</th>
                <th>아황산가스농도</th>
                <th>오존농도</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
    <script>
        /*
            Javascript의 화살표 함수(람다식)
            -Javascript 6버전부터 제공하는 가독성을 높여주는 문법
            -기존의 익명함수를 표현할 때 쓴다.

            [표현법]
            -기존의 방식
            function(){
                실행할내용
            }

            -화살표함수를 이용한 방식
            ()=>{
                실행할내용
            }

            [사용 예시]
            기존 표현법         |       화살표함수 표현법
            ======================================================
            function(){}        |       ()=>{}
            ------------------------------------------------------
            function(a){}       |       a=>{}
            ------------------------------------------------------
            function(a,b){}     |       (a,b)=>{}
            ------------------------------------------------------
            function(){         |       ()=>10
                return 10;      |       
            }                   |

        */
        $(function() {
            $("#btn1").click(()=>{
                //응답데이터를 XML 형식으로 돌려받은 경우
                $.ajax({
                    url:"air.do",
                    data:{location:$("#location").val()},
                    success:data => {
                        //console.log(data);

                        //=>xml도 마찬가지로 마크업 언어 (태그들로 이루어져 있음)
                        //=>부모요소 - 자식요소 관계가 명확
                        //=>jQuery 탐색메소드들 중에서 find() 메소드를 활용
                        //기준이되는요소.find(): 기준이 되는 요소의 하위 요소들 중에서 특정 요소를 찾을 때 사용

                        //console.log($(data).find("item"));

                        //xml 형식의 응답데이터를 받았을 때
                        //1.응답데이터 안에 실제 데이터가 담겨있는 요소들(item 태그들)선택
                        var itemArr=$(data).find("item");   //[item, item, ...]

                        var value="";

                        //2.반복문을 이용해서 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들기
                        //=>반복문 역할을 해주는 each (jQuery 제공 메소드) 메소드 활용
                        itemArr.each((index, item)=>{
                            //console.log(item);
                            //console.log($(item).find("stationName")); //
                            //console.log($(item).find("stationName").text()); //시작태그와 종료태그 사이의 문구만 추출
                            value+="<tr>"
                                + "<td>" +$(item).find("stationName").text() +"</td>"
                                + "<td>" +$(item).find("dataTime").text() +"</td>"
                                + "<td>" +$(item).find("khaiValue").text() +"</td>"
                                + "<td>" +$(item).find("pm10Value").text() +"</td>"
                                + "<td>" +$(item).find("coValue").text() +"</td>"
                                + "<td>" +$(item).find("no2Value").text() +"</td>"
                                + "<td>" +$(item).find("so2Value").text() +"</td>"
                                + "<td>" +$(item).find("o3Value").text() +"</td>"
                                +"</tr>";
                        });
                        //3.동적으로 만들어낸 요소를 화면에 출력
                        $("#result1>tbody").html(value);
                    },
                    error:()=>{
                        console.log('ajax로 대기오염정보 불러오기 실패');
                    }
                })

                //응답데이터를 JSON 형식으로 돌려받은 경우
                /*
                $.ajax({
                    url:"air.do",
                    data:{
                        location:$("#location").val()
                    },
                    success:function(data){
                        console.log(data);
                        console.log(data.response.body.items);  //[item, item, ...]

                        var itemArr=data.response.body.items;

                        var value="";
                        for(var i=0; i<itemArr.length; i++){
                            //console.log(itemArr[i]);
                            var item=itemArr[i];
                            value+="<tr>"
                                +"<td>"+item.stationName +"</td>"
                                + "<td>" +item.dataTime +"</td>"
                                + "<td>" +item.khaiValue +"</td>"
                                + "<td>" +item.pm10Value +"</td>"
                                + "<td>" +item.coValue +"</td>"
                                + "<td>" +item.no2Value +"</td>"
                                + "<td>" +item.so2Value +"</td>"
                                + "<td>" +item.o3Value +"</td>"
                                +"</tr>";
                        }
                        $("#result1>tbody").html(value);
                    },
                    error:function(){
                        console.log("ajax로 대기오염정보 불러오기 실패");
                    }
                })
                */
            })
            
        })
    </script>
</body>
</html>