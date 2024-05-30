<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Hello!!</h1>
	<h2>이름은 ${myname}</h2>
	<h2>점수는 ${myscore}</h2>
	
	<h3>Get방식요청(요청주소에 param이용 : 값이 동일, field존재, 값이 존재하지 않음)</h3>
	<form action="${pageContext.servletContext.contextPath}/sample/hello6.do">
		email : <input type="email" name="email" value="hello@gmail.com"><br>
		password : <input type="password" name="pwd" value="1234"><br>
		phone : <input type="text" name="phone" value="010-1234-1234"><br>
		<input type="submit" value="서버전송(get)">
	</form>
	
	<h3>Post방식요청</h3>
	<form action="${pageContext.servletContext.contextPath}/sample/hello6.do" method="post">
		email : <input type="email" name="email" value="hello@gmail.com"><br>
		password : <input type="password" name="pwd" value="1234"><br>
		phone : <input type="text" name="phone" value="010-1234-1234"><br>
		<input type="submit" value="서버전송(post)">
	</form>
</body>
</html>