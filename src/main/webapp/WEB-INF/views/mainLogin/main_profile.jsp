<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String company = (String) request.getSession().getAttribute("company");
	String id = (String) request.getSession().getAttribute("id");
//	String pw = (String) request.getSession().getAttribute("pw"); /**/
	String gubun = (String) request.getSession().getAttribute("gubun"); /**/
	String empno = (String) request.getSession().getAttribute("empno");
	String empnm = (String) request.getSession().getAttribute("empnm");
	String deptno = (String) request.getSession().getAttribute("deptno");
	String deptnm = (String) request.getSession().getAttribute("deptnm");
	
	String imgName = (String) request.getAttribute("imgName");
	boolean noImage = (Boolean) request.getAttribute("noImage");
%>
<html>
<head>
<meta charset="UTF-8">
<title>프로필</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script language=javascript>
	function menu_action(url) {
		parent.location.href =  './MainLogOut.em' /* 'out_logout.jsp'; */
<%--//document.myform.target = 'parent';
    //document.myform.action = url;
    //document.myform.submit();--%>
	}

	function popup(pwform){
		var url = "./MainPwChangePopup.em"
		var name = "pwChange"; 
		window.open(url, name, 'width=535, height=520, toobar=no, status= no, left = 600, top=200 ' 
				+'location=no, directiories = no, satus=no, menubar=no, scrollbars=no, copyhistoy=no' 
				+'resizeble=no');		
	}
	
	function popupInfo(pwform){
		var url = "./MainInfoChangePopup.em"
		var name = "infoChange";
		window.open(url, name, 'width=530, height=610, toobar=no, status= no, left = 560, top=160 ' 
				+'location=no, directiories = no, satus=no, menubar=no, scrollbars=no, copyhistoy=no' 
				+'resizeble=no');		
	}
	
</script>
</head>
<body>

	<!---------------------------------------- Profile ----------------------------------------- -->

	<div style="padding-top: 6px; padding-left: 4px;">
	
		<div style="text-align: center;">
			<h4>My Profile</h4>
				
		<% if (noImage) { %>
			<img src="./resources/images/img_avatar3.png"
				style="height: 106px; width: 106px" alt="Profile Image" />
		<% } else { %>
			<img src="./resources/tempImg/<%= imgName %>"
				style="height: 106px; width: 106px" alt="Profile Image" />
		<% } %>

				
		<div style="padding-top: 6px; padding-bottom: 6px;">
		<table align="center" style="width: 65%">
		<td>
		<form name="pwform" method="post">
		<button class="btn btn-info btn-sm" name="pwChange"
			onclick="javascript:popup(pwform)" style="width: auto;" >
			비밀번호 변경</button>
		</form>
		</td>
		<td>
		<td>
		<button class="btn btn-info btn-sm" name="infoChange"
			onclick="javascript:popupInfo('pwform')" style="width: auto;" >
			정보수정</button>
			</td>
		<td>
			<form name="myform" method="post" class="form">
				<button class="btn btn-info btn-sm" type="button"  style="width: auto;
				 "onclick="javascript:menu_action();">로그아웃</button>
			</form>
		 </td>
		</table>		
		</div>

	
		</div>


<!-- --------------------------------로그인 사용자 정보------------------------------------------------ -->
		<div class="alert alert-info">
			<strong>Info :</strong><br />
			<%
				if (gubun != null && gubun.equals("M")) {
			%><%-- 관리자 --%>
			<strong>ID.</strong>
			<%=id%><br /> <strong>Name.</strong>
			<%=empnm%><br /> &nbsp;<br /> &nbsp;<br /> &nbsp;<br />
			<%
				} else {
			%><%-- 유저 --%>
			<strong>Dept Code.</strong>
			<%=deptno%><br /> <strong>Dept Name.</strong>
			<%=deptnm%><br /> <strong>ID.</strong>
			<%=id%><br /> <strong>Emp No.</strong>
			<%=empno%><br /> <strong>Emp Name.</strong>
			<%=empnm%><br />
			<%
				}
			%>
		</div>
		
	</div>

</body>
</html>











