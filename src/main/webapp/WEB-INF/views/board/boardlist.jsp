<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

	setTimeout(() => {
		var message = "${resultMessage}";
		if(message != "") alert(message);
	}, 500);/* 0.5초후에 */
	
	
	
</script>
</head>
<body>
	<%@ include file="../common/header.jsp" %>
	<a href="${path}/board/boardinsert.do">게시글 등록</a>
	<h1>Board목록</h1>
	<form action="${path}/board/selectDelete.do">
	<table border="1">
		<tr>
			<th>선택</th>
			<th>bno</th>
			<th>title</th>
			<th>content</th>
			<th>writer</th>
			<th>pic</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="board" items="${blist}">
			<tr>
				<td>
					<input type="checkbox" name="checkBno" value="${board.bno}">
				</td>
				<td><a href="${path}/board/boardDetail.do?bno=${board.bno}">${board.bno}</a></td>
				<td>${board.title}</td>
				<td>${board.content}</td>
				<td>${board.writer}</td>
				
				<td>
					<%-- <img alt="${board.title}" width="50" height="50" src="/upload/${board.pic}"> --%>
					<img alt="${board.title}" width="50" height="50" src="${path}/resources/uploads/${board.pic}">
					
					 <a href="/download.do?filename=${board.pic}">내려받기</a>
				</td>
				
				<td>${board.create_date}</td>
				<td>
					<input type="button" value = "삭제하기" onclick="location.href='${path}/board/boardDelete.do?bno=${board.bno}'">
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" value="선택삭제">
	</form>
</body>
</html>
<!-- 
 form tag내의 <button>은 submit으로 수행한다!
 -->
