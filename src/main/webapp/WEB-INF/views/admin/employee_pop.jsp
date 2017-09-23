<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
	String sComp_cd = (String) request.getSession().getAttribute("company"); // session company
	//String sComp_cd = "C001";
	String company = sComp_cd;
//	if(request.getParameter("company") != null) {
//		company = request.getParameter("company");
//	}
	String idx = "";
	if(request.getParameter("idx") != null) {
		idx = request.getParameter("idx");
	}
	
	String searchGubun = "";
	String searchInput = "";
	
	List list = (List) request.getAttribute("list");
	int listCount = list.size();
	
	searchGubun = (String) request.getAttribute("searchGubun");
	searchInput = (String) request.getAttribute("searchInput");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 검색</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
function selRow(idx){
	var form = opener.document.getElementById("listForm");
	var openerIdx = opener.document.getElementById("idx").value;
	
	opener.document.forms["listForm"]["deptManager"+openerIdx].value = document.getElementById("empNo"+idx).value;
	opener.document.forms["listForm"]["deptManagerNm"+openerIdx].value = document.getElementById("empNm"+idx).value;
	
	winClose();
}
function winClose(){
	window.close();
}
</script>
</head>
<body class="w3-container">

<div class="w3-container">
<h3>사원 검색</h3>

<table class="w3-table w3-striped w3-bordered w3-card-4" id="myTable">
<thead>
<tr class="w3-blue">
<th class="w3-center">사번</th>
<th class="w3-center">이름</th>
<th class="w3-center">부서</th>
<th class="w3-center">직급</th>
</tr>
</thead>
<%
	String	comp_cd;		//	회사 코드
	String	emp_no;		//	사번
	String	emp_nm;		//	이름
	String	dept_no;		//	부서코드
	String	emp_role_cd;//	직급코드
	String	dept_nm;		// 부서명**
	String	emp_role_nm;//직급명**
	
	if(listCount == 0) { %>
<tr>
<td  colspan="4" class="w3-center">조회된 자료가 없습니다.</td>
</tr>
<%
	} else {
	
	for(int i = 0; i < listCount ; i++) {
		spring.mybatis.gw.admin.dto.EmployeeDTO data = (spring.mybatis.gw.admin.dto.EmployeeDTO)list.get(i);
		comp_cd = data.getComp_cd();
		if (comp_cd==null) {comp_cd = "";}		
		emp_no = data.getEmp_no();
		if (emp_no==null) {emp_no = "";}
		emp_nm = data.getEmp_nm();
		if (emp_nm==null) {emp_nm = "";}
		dept_no = data.getDept_no();
		if (dept_no==null) {dept_no = "";}
		emp_role_cd = data.getEmp_role_cd();
		if (emp_role_cd==null) {emp_role_cd = "";}
		dept_nm = data.getDept_nm();
		if (dept_nm==null) {dept_nm = "";}
		emp_role_nm = data.getEmp_role_nm();
		if (emp_role_nm==null) {emp_role_nm = "";}		
		
%>
<tr ondblclick="selRow(<%= i %>);" onclick="selRow(<%= i %>);">
<td class="w3-center"><%= emp_no %><input id="empNo<%= i %>"  type="hidden" value="<%= emp_no %>" /></td>
<td class="w3-center"><%= emp_nm %><input id="empNm<%= i %>"  type="hidden" value="<%= emp_nm %>" /></td>
<td class="w3-center"><%= dept_nm %><input id="deptNo<%= i %>"  type="hidden" value="<%= dept_no %>" /></td>
<td class="w3-center"><%= emp_role_nm %><input id="roleCd<%= i %>"  type="hidden" value="<%= emp_role_cd %>" /></td>
</tr>
<% } } %>
</table>

<br /><br />
</div>
<div class="w3-center">
<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round "
	id="closeButton" onclick="winClose();" style="display: inline-block;">닫기</button>
</div>

</body>
</html>