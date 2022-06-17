<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		table * {margin:5px;}
        table {width:100%;}
	</style>
</head>
<body>
        
    <jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="list.bo">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
                    		<c:when test="${ empty b.originName }">
                    			첨부파일이 없습니다.
                    		</c:when>
                    		<c:otherwise>
                        		<a href="${ b.changeName }" download="${ b.originName }">${ b.originName }</a>
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${ b.boardContent }</p></td>
                </tr>
            </table>
            <br>

			<c:if test="${ (not empty loginUser) and (b.boardWriter eq loginUser.userId) }">
	            <div align="center">
	                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
	                <a class="btn btn-primary" onclick="postFormSubmit(1);">수정하기</a>
	                <a class="btn btn-danger" onclick="postFormSubmit(2);">삭제하기</a>
	            </div>
	            <br><br>
	            
	            <form id="postForm" action="" method="post">
	            	<input type="hidden" name="bno" value="${ b.boardNo }">
	            	<input type="hidden" name="filePath" value="${ b.changeName }">
	            </form>
	            
	            <script>
	            	function postFormSubmit(num) {
	            		
	            		if(num == 1) { // 수정 요청으로 action 속성값 바꾸기
	            			
	            			$("#postForm").attr("action", "updateForm.bo").submit();
	            		}
	            		else { // 삭제 요청으로 action 속성값 바꾸기
	            			$("#postForm").attr("action", "delete.bo").submit();
	            		}
	            	}
	            </script>
            </c:if>

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <c:choose>
                            <c:when test="${empty loginUser}">
                                <th colspan="2">
                                    <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;" readonly>로그인한 사용자만 이용 가능한 서비스입니다. 로그인 후 이용 바랍니다.</textarea>
                                </th>
                                <th style="vertical-align:middle"><button class="btn btn-secondary" disabled>등록하기</button></th>
                            </c:when>
                            <c:otherwise>
                                <th colspan="2">
                                    <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                                </th>
                                <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
                            </c:otherwise>
                        </c:choose>
                        
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount"></span>)</td>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    <script>
        $(function(){
            selectReplyList();
        })
        function addReply(){
            //댓글 작성
            
            //댓글 내용이 있는지 먼저 조건검사 후에 요청
            //=>required 속성은 form 태그 내부에서만 유효하기 때문
            if($("#content").val().trim().length!=0){
                //입력한 내용물 기준으로 공백 제거 후에도 문자열의 길이가 0이 아니라면=>유효한 내용물이 있다고 간주
                $.ajax({
                    url:"rinsert.bo",
                    data:{
                        refBoardNo: '${ b.boardNo}',
                        replyContent: $("#content").val(),
                        replyWriter: "${loginUser.userId}"
                    },
                    success:function(result){
                        if(result=="success"){
                            selectReplyList();
                            $("#content").val("");
                        }else{
                            alertify.alert("댓글 작성 실패","댓글 등록에 실패했습니다.");
                        }
                    },
                    error:function(){
                        console.log("댓글 작성용 ajax 통신 실패!");
                    }
                });
            }else{
                alertify.alert("댓글 작성 실패", "댓글 작성 후 등록 요청해주세요.");
            }
        }
        function selectReplyList(){
            //해당 게시글에 딸린 댓글리스트 조회
            $.ajax({
                url:"rlist.bo",
                data:{bno: '${ b.boardNo }'},
                success:function(result){
                    let resultStr="";
                    for (const r of result) {
                        resultStr +=   "<tr>"+
                                            "<th>"+ r.replyWriter +"</th>"+
                                            "<td>"+ r.replyContent +"</td>"+
                                            "<td>"+ r.createDate +"</td>"+
                                        "</tr>";
                    }
                    $("#replyArea tbody").html(resultStr);
                    $("#rcount").text(result.length);
                },
                error:function(){
                    console.log("댓글리스트 조회용 ajax 통신 실패")
                }
            })
        }
    </script>
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>