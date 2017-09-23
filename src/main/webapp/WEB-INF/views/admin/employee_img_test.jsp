<%--
 //******************************************************************************
//  프로그램ID : employee_img_test.jsp
//  프로그램명 : 사원 이미지 업로드 테스트
//  관련 DB 테이블 : EMPLOYEE_INFO
//  기타 DB 테이블 : 
//  작  성  자 : 조 수 정 (Sujung Jo)
//  작  성  일 : 2016.11.23.
//******************************************************************************
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spring.mybatis.gw.admin.dto.EmployeeDTO" %>
<%
EmployeeDTO employee = (EmployeeDTO) request.getAttribute("employee");
String imgName = (String) request.getAttribute("imgName");
boolean noImage = (Boolean) request.getAttribute("noImage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 이미지 업로드 테스트</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">

</script>
</head>
<body class="w3-container" >

<div class="w3-container">
<h2>사원 이미지 업로드 테스트</h2>
<form action="EmployeeImgTestUp.admin" enctype="multipart/form-data" method="post">
	<input name="compCd" type="text" value="<%= employee.getComp_cd() %>"/>
	<input name="empNo" type="text" value="<%= employee.getEmp_no() %>"/>
	<input type="file" name="img" /><br /><br />
	<input type="submit" value="이미지 업로드" /><br /><br />
</form>
<% if(noImage) { %>
No Image<br/>
<img src="./resources/images/img_avatar3.png" />
<% } else { %>
<img src="./resources/tempImg/<%= imgName %>" />
<% } %>
</div>

</body>
</html>