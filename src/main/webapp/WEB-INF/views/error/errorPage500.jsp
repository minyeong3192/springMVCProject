<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage = "true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${errorMessage}</h1>
	<h2>서버오류. 프로그램실행오류(서버의 console창 확인!)</h2>
	<h4><%=exception %></h4>
	<h2>${url}</h2>
	<h3>[500오류]</h3>
</body>
</html>