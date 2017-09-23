<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@page import="spring.mybatis.gw.admin.dto.*"%>
<%
	String cNM = (String)request.getAttribute("compnm");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=cNM %></title>
</head>
<%-- <%
}
%> --%>
<frameset cols="250px,*" frameboard="NO" border="0" framespacing="0" name="main">
	<frameset rows="350px,*">
		<frame src="./MainMainProfile.em"  name="profile" scrolling="NO" noresize />
		<frame src="./MainMainMenu.em" name="menu" scrolling="NO" noresize />
	</frameset>
	<frame src="./BoardList.bo" name="board" /> 
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>