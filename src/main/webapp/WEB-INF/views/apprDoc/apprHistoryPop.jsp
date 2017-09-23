<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="spring.mybatis.gw.apprdoc.dto.*" %>
<%@ page import="spring.mybatis.gw.apprdoc.dao.*" %>
<%@ page import="spring.mybatis.gw.apprdoc.dao.ApprStatusDAO" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import= "org.springframework.web.context.WebApplicationContext" %>


<%
	List<HistoryPopDTO> list = (List<HistoryPopDTO>)request.getAttribute("list");
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) request).getSession().getServletContext());
	ApprStatusDAO boardDao = (ApprStatusDAO)wac.getBean("apprStatusDAO");	

	//ApprDocDAO boardDao = new ApprDocDAO();
	String comp_cd = (String)session.getAttribute("company");
	
	System.out.println("jsp 페이지 리스트 수 체크"+list.size());
	

%>    

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재 이력</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<style type="text/css">
	.div1 {width : 700px; align : center;}
	.p1 {text-align : center;}
</style>

</head>
<body  class="w3-container w3-text-theme">

<div class="div1">
<br>
<div class="w3-display-container " style="height:24px;">
	<div class="w3-display-left w3-padding-small " style="font-size:150%">결재 이력</div>	
</div>
<br>


	
	<table class="w3-table w3-striped w3-bordered w3-card-4" >
	
	<tr class="w3-theme">
		<th class="w3-center" style="width:100px">부서</th>
		<th class="w3-center" style="width:70px">직급</th>
		<th class="w3-center" style="width:100px">이름</th>
		<th class="w3-center" style="width:80px">결재 종류</th>
		<th class="w3-center" style="width:150px">결재시간</th>				
	</tr>
<%
	if(list.size() > 0){//데이터가 하나도 저장하지 않았을 경우 걸러냄
%>
	<%for(int i=0; i< list.size(); i++){ %>	
	
		<% 
			int myHistory = boardDao.getMyApprHistory(list.get(i). getEmp_no(), comp_cd, Integer.parseInt(list.get(i).getAppr_no()));//결재 서류 이력 중 내 이력이 있는지 체크하기
			String mySatus = boardDao.getMyHistoryStatus(list.get(i). getEmp_no(), comp_cd, Integer.parseInt(list.get(i).getAppr_no()));//결재 서류 이력 중 내 이력의 상태 체크하기
		%>
	<tr>
		<td  class="w3-center"><%=list.get(i).getDept_nm()%></td>
		<td  class="w3-center"><%=list.get(i).getRole()%></td>
		<td  class="w3-center"><%=list.get(i).getEmp_nm()%></td>
		<td  class="w3-center">
			<%if(myHistory > 0){ %>
				<%=mySatus  %>
			<%}else{ %>
				미결
			<%} %>		
		</td>
		<td  class="w3-center">
			<%if(myHistory > 0){ %>
				<%=list.get(i).getInsert_dt()  %>			
			<%} %>		
		</td>
	</tr>
	
		<%} %>
	<%
	    }
	else
		{
		%>
	<tr>		
		<td class="w3-center" colspan="5">	결재이력이 없습니다.</td>
	</tr>
	<%
		}
	%>
	</table>
</div>
	    
</body>
</html>