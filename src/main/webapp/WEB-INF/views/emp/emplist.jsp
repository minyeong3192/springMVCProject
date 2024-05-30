<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
	<%-- <c:set var="path" value="${pageContext.servletContext.contextPath}" /> --%>
	<%-- <p>${path}</p> --%>
	<a href="${path}/emp/empInsert.do">신규직원등록</a><hr>
	
	
	<form id="conditionForm"> 
	<!-- 부서로 조회하기! -->
	부서선택 : 
	<select id="deptSelect" name="deptSelect">
		<option value="0">전체</option>
		
		<c:forEach var="dept" items="${deptlist}">
			<option ${deptSelect==dept.department_id?"selected":""} value="${dept.department_id}">${dept.department_name}</option>
		</c:forEach>	
	</select>
	
	
	<!-- 직책으로 조회하기! -->
	직책선택 : 
	<select id="jobSelect" name="jobSelect">
		<option value="all">전체</option>
		
		<c:forEach var="job" items="${joblist}">
			<option ${jobSelect==job.job_id?"selected":""} value="${job.job_id}">${job.job_title}</option>
		</c:forEach>	
	</select>
	
	
	<input type="date" id="hdate" name="hdate" value = "${hdate}"> 
	<input type="number" id="salary" name="salary" value = "${salary}">
	</form>
	<hr>
	<button onclick="f_deptSelect()">부서조회</button>
	<button onclick="f_jobSelect()">직책별 조회</button>
	<button onclick="f_condition()">4가지 조건조회</button>
	
	<h1>직원목록</h1>
	<table border="1">
		<thead>
			<tr>
				<th>직원번호</th>
				<th>이름</th>
				<th>성</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>입사일</th>
				<th>직책</th>
				<th>급여</th>
				<th>커미션</th>
				<th>매니저</th>
				<th>부서</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="emp" items="${emplist}">
				<tr>
					<td>
						<a href="${path}/emp/empDetail.do?empid=${emp.employee_id}">${emp.employee_id}</a>
					</td>
					<td>${emp.first_name}</td>
					<td>${emp.last_name}</td>
					<td>${emp.email}</td>
					<td>${emp.phone_number}</td>
					<td>${emp.hire_date}</td>
					<td>${emp.job_id}</td>
					<td>${emp.salary}</td>
					<td>${emp.commission_pct}</td>
					<td>${emp.manager_id}</td>
					<td>${emp.department_id}</td>
					<td><button onclick="location.href='${path}/emp/empDelete.do?empid=${emp.employee_id}'">삭제</button></td>
				</tr>
			</c:forEach>	
		</tbody>
</table>

<script>
	function f_deptSelect(){
		var deptid = $("#deptSelect").val();
		//alert(deptid);
		location.href = "${path}/emp/empAll.do?deptid="+deptid;
	
	}
	function f_jobSelect(){
		var jobid = $("#jobSelect").val();
		//alert(deptid);
		location.href = "${path}/emp/empAll.do?jobid="+jobid;
	}
	function f_condition(){
		/*var deptid = $("#deptSelect").val();
		var jobid = $("#jobSelect").val();
		var hdate = $("#hdate").val();
		var salary = $("#salary").val();

		console.log(deptid);
		console.log(jobid);
		console.log(hdate);
		console.log(salary);
		
		location.href = "${path}/emp/empAll.do";*/

		//deptSelect=50&jobSelect=FI_ACCOUNT&hdate=2005-01-01&salary=5000
		var param = $("#conditionForm").serialize();
		//console.log(param);
		location.href = "${path}/emp/empAll2.do?"+param;
	}
	
	</script>
</body>

</html>