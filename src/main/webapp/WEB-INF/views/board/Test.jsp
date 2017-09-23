<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="spring.mybatis.gw.board.dto.BoardDTO"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>TEST</h1>
	
	<%
		List<BoardDTO> a = (List<BoardDTO>)request.getAttribute("list");	
	%>
	
	
	
	<%= a.get(0).getCOMP_CD() %>
	<%= a.get(0).getCONTENT() %>	
	<%= a.get(0).getEMP_NM()   %>	
	<%= a.get(0).getM_YN()     %>
	<%= a.get(0).getNO()     %>
	<%= a.get(0).getINSERT_DT()     %>
	<%= a.get(0).getSUBJECT()     %>
	<%= a.get(0).getSUB_SUBJECT()     %>
	<%= a.get(0).getREADCOUNT()     %>
	<%= a.get(0).getGUBUN()  %>	
	
	
	<%-- <%= a.get(0).getINSERT_ID()     %> --%>
	
	
	<br /><br /><br />
	 DEPT_NO : <%= a.get(0).getDEPT_NO() %><br />
	 EMP_NO : <%= a.get(0).getEMP_NO()     %><br />
	
	 <%-- startrow : <%= a.get(0).getStartrow() %><br />
	 endrow : <%= a.get(0).getEndrow() %> --%>
	
	
	
	
</body>
</html>