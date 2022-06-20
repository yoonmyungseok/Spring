<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>title</title>
</head>

<body>
    <jsp:include page="common/header.jsp" />
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h4>게시글 TOP 5</h4>
            <br>
            <a href="list.bo" style="float:right;">더보기&raquo;</a>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    <th>첨부파일</th>
                </thead>
                <tbody>
                    <!--현재 조회수가 가장 높은 상위 5개 게시글 조회해서 뿌리기(ajax)-->
                </tbody>
            </table>
        </div>
        <br><br>
    </div>
    <script>
        $(function () {
            topBoardList();

            //실시간으로 변동을 주기 위해 interval 걸어줄것
            //setInterval(topBoardList, 1000);

            //1분==60초==60000ms
            //3분 180000ms
            setInterval(topBoardList, 180000);

            //게시글에 대한 클릭 이벤트
            //=>동적으로 만들어진 요소이기 때문에 클릭 이벤트가 제대로 걸리지 않음
            /*
            $("#boardList>tbody>tr").click(function () {
                location.href = "detail.bo?bno=" + $(this).children().eq(0).text();
            });
            */

            //동적으로 만들어진 요소에 이벤트를 걸려면 on 메소드 활용
            $("#boardList>tbody").on("click", "tr", function () {
                location.href = "detail.bo?bno=" + $(this).children().eq(0).text();
            })
        })
        function topBoardList() {
            $.ajax({
                url: "topList.bo",
                success: function (result) {
                    //console.log(result);
                    let resultStr = "";
                    for (const i in result) {
                        resultStr +=
                            "<tr>"
                            + "<td>" + result[i].boardNo + "</td>"
                            + "<td>" + result[i].boardTitle + "</td>"
                            + "<td>" + result[i].boardWriter + "</td>"
                            + "<td>" + result[i].count + "</td>"
                            + "<td>" + result[i].createDate + "</td>"
                            + "<td>";
                        if (result[i].originName != null) {//첨부파일이 있을 경우
                            resultStr += "⭐️";
                        }
                        resultStr +=
                            "</td>"
                            + "</tr>";
                    }
                    $("#boardList>tbody").html(resultStr);
                },
                error: function () {
                    console.log("조회수 top5 게시글 조회용 ajax 통신 실패");
                }
            })
        }
    </script>
    <jsp:include page="common/footer.jsp" />
</body>

</html>