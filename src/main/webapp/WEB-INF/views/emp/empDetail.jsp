<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!-- include지시자는 파일을 합쳐서 컴파일한다. -->
	
	<h1>직원상세보(Spring)</h1>
	
	<form action="${path}/emp/empDetail.do" method="post" > 
    <div class="mb-3 mt-3">
      <label for="employee_id">1.직원번호 :</label>
      <input type="number" class="form-control" id="employee_id" placeholder="Enter employee_id" name="employee_id" value="${empInfo.employee_id}">
    </div>
    <div class="mb-3 mt-3">
      <label for="first_name">2.이름 :</label>
      <input type="text" class="form-control" id="first_name" placeholder="Enter first_name" name="first_name" value="${empInfo.first_name}">
    </div>
    <div class="mb-3 mt-3">
      <label for="last_name">3.성 :</label>
      <input type="text" class="form-control" id="last_name" placeholder="Enter last_name" name="last_name" value="${empInfo.last_name}">
    </div>
    <div class="mb-3 mt-3">
      <label for="email">4.이메일 :</label>
      <input type="text" class="form-control" id="email" placeholder="Enter email" name="email" value="${empInfo.email}">
    </div>
    <div class="mb-3 mt-3">
      <label for="phone_number">5.phone_number :</label>
      <input type="text" class="form-control" id="phone_number" placeholder="Enter phone_number" name="phone_number" value="${empInfo.phone_number}">
    </div>
    <div class="mb-3 mt-3">
      <label for="hire_date">6.hire_date :</label>
      <input type="date" class="form-control" id="hire_date" placeholder="Enter hire_date" name="hire_date" value="${empInfo.hire_date}">
    </div>
    
    <%-- ScriptLet문법보다는, EL(반복문없음): ${}, JSP문법: <c:forEach>을 사용하는 것이 좋다. 
    	for(JobDTO job:joblist){}
    --%>
    	
    <div class="mb-3 mt-3">
      <label for="job_id">7.job_id :</label>
      <select name="job_id">
      	<c:forEach items="${joblist}" var="job">
      		<option value="${job.job_id}"  ${empInfo.job_id == job.job_id ? "selected" : ""}>${job.job_title}</option>
      		${empInfo.job_id}/${job.job_id}
      	</c:forEach>
      </select>
    </div>
    
    <div class="mb-3 mt-3">
      <label for="salary">8.salary :</label>
      <input type="number" class="form-control" id="salary" placeholder="Enter salary" name="salary" value="${empInfo.salary}">
    </div>
    <div class="mb-3 mt-3">
      <label for="commission_pct">9.commission_pct :</label>
      <input type="text" class="form-control" id="commission_pct" placeholder="Enter commission_pct" name="commission_pct" 
      value="${empInfo.commission_pct}">
    </div>
    
    <div class="mb-3 mt-3">
      <label for="manager_id">10.manager_id :</label>
      <select name="manager_id">
      	<option value="0">매니저없음</option>
      	<c:forEach items="${mlist}" var="manager" >
      		<option value="${manager.employee_id}" ${empInfo.manager_id == manager.employee_id ? "selected" : ""}>
      			${manager.employee_id} / ${manager.fullname}
      		</option>
      	</c:forEach>
      </select>
    </div>
    
    <div class="mb-3 mt-3">
      <label for="department_id">11.department_id :</label>
      <select name="department_id">
      	<option value="0">부서없음</option>
      	<c:forEach items="${deptlist}" var="dept">
      		<option value="${dept.department_id}" ${empInfo.department_id == dept.department_id ? "selected" : ""}>
      		 ${dept.department_id} / ${dept.department_name}</option>
      	</c:forEach>
      </select>
    </div>
    
    <input type="submit" class="btn btn-primary" value="수정하기">
  </form>
	
</body>
</html>