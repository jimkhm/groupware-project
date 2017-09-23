<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String company = (String)request.getSession().getAttribute("company");
	String id = (String)request.getSession().getAttribute("id");
	String pw = (String)request.getSession().getAttribute("pw"); /**/
	String gubun = (String)request.getSession().getAttribute("gubun"); /**/
	String empno = (String)request.getSession().getAttribute("empno");
	String empnm = (String)request.getSession().getAttribute("empnm");
	String deptno = (String)request.getSession().getAttribute("deptno");
	String deptnm = (String)request.getSession().getAttribute("deptnm");
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴</title>
<style type="text/css">
	 a {
	     background-color: transparent;
	     text-decoration: none;
	     font-size: 20px;
	     font-weight: bold; 
 	 }
 	 
 	 b {
 	 	font-size: 30px;
 	 	color: blue;
 	 }
	
	table {
	    font-family: arial, sans-serif;
	    border-collapse: collapse;
	    font-weight:bold;
		box-shadow:5px 5px 5px #A9A9A9;
		border-color: #ffb84d;
		color: #ffb84d;
	}
	
	td {
	    border: 1px solid #dddddd;
	    text-align: left;
	    padding: 8px;
	}
	
	tr:nth-child(even) {
	    background-color: #dddddd;
	}
	
</style>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<script language=javascript>
function menu_color(idx){
	for (var i = 1; i <= 7; i++){
		<% if(gubun != null && gubun.equals("U")) { %><%-- 유저 --%>
			if(i==2 || i==3 || i==4 || i==5 || i==6){
				continue;
			}
		<% } %>
		
		var menu = document.getElementById('menu'+i);
		
		if(idx == i){
			menu.className = menu.className.replace(" w3-cyan", " w3-light-blue");			
			menu.className = menu.className.replace(" w3-text-indigo", " w3-text-white");
		} else {
			menu.className = menu.className.replace(" w3-light-blue", " w3-cyan");
			menu.className = menu.className.replace(" w3-text-white", " w3-text-indigo");
		}
	}
}
function menu_action(url){
    document.myform.target = 'board';
    document.myform.action = url;
    document.myform.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" background="" text="#003366" vlink="#006699" alink="#FF6633">

<form name="myform" method="post">

<nav class="w3-sidenav w3-light-grey" style="width:260px">
	<!-- 전체 -->
  <a id="menu1" class=" w3-light-blue w3-text-white " onclick="menu_color(1);" href="javascript:menu_action('<%= request.getContextPath() %>/BoardList.bo');">공지 사항</a>
  
  <% if(gubun != null && gubun.equals("M")) { %><%-- 관리자 --%>
  <a id="menu2" class=" w3-cyan w3-text-indigo " onclick="menu_color(2);" href="javascript:menu_action('<%= request.getContextPath() %>/CompanyInfo.admin');">회사 정보</a>
  <a id="menu3" class=" w3-cyan w3-text-indigo " onclick="menu_color(3);" href="javascript:menu_action('<%= request.getContextPath() %>/DeptInfo.admin');">부서 정보</a>
  <a id="menu4" class=" w3-cyan w3-text-indigo " onclick="menu_color(4);" href="javascript:menu_action('<%= request.getContextPath() %>/DeptTree.admin')">부서 정보(조직도)</a>
  <a id="menu5" class=" w3-cyan w3-text-indigo " onclick="menu_color(5);" href="javascript:menu_action('<%= request.getContextPath() %>/EmployeeInfo.admin');">사원 정보</a>
  <a id="menu6" class=" w3-cyan w3-text-indigo " onclick="menu_color(6);" href="javascript:menu_action('<%= request.getContextPath() %>/CodeMgr.cd');">공통 코드</a>

  <%} else { %><%-- 유저 --%>
  <a id="menu7" class=" w3-cyan w3-text-indigo " onclick="menu_color(7);" href="javascript:menu_action('<%= request.getContextPath() %>/ApprList.appr');">전자 결재</a>
  <%}%>
</nav>

</form>

</body>
</html>