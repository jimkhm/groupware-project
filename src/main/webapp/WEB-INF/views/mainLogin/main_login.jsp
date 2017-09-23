<%--
 //******************************************************************************
//  프로그램ID : main_login.jsp
//  프로그램명 : 로그인 화면
//  관련 DB 테이블 : EMPLOYEE_INFO 
//  기타 DB 테이블 : SEQ_INFO, COMPANY_INFO
//  작  성  자 : 조 중 석
//  작  성  일 : 2016.10.05.
//******************************************************************************
//  수  정  자 : 조 중 석 
//  수  정  일 : 2016.11.14.
//  변경 내용 : 
//****************************************************************************** 
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="spring.mybatis.gw.admin.dto.*" %>
<%
	List list = (List)request.getAttribute("list");
	int listCount = list.size();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자결재 로그인</title>
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
	function check() {
		var id = loginform.id.value;
		var pass = loginform.pw.value;

		if (id.length == 0) {
			alert("아이디를 입력하세요.");
			loginform.id.focus();
			return false;
		}
		if (pass.length == 0) {
			alert("비밀번호를 입력하세요.");
			loginform.pw.focus();
			return false;
		}

		return true;
	}
</script>
<style>
form {
    border: 5px solid #f1f1f1;
    width: 1300px;
}

input[type=text], input[type=password] {
    width: auto;
    padding: 10px 15px;
    margin: 6px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

select{
	width: auto;
    padding: 5px 10px;
    margin: 6px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}


button {
    background-color: #ff9900;
    color: white;
    padding: 20px 20px;
    margin: 6px 0;
    border: none;
    cursor: pointer;
    width: auto;
    box-shadow: 1px 1px 1px #A9A9A9;
}

.imgcontainer {
    text-align: center;
    margin: 50px 0 40px 0;
}

img.main {
    width: 70%;
}

.container {
    padding: 16px;
}

.btnCancel {
	padding: 3px;
	text-align: left;
}

span.psw {
    float: right;
    padding-top: 16px;
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
    span.psw {
       display: block;
       float: none;
    }
}
</style>
</head>

<body>
<div align="center" style="padding-top: 5%;">
<form action="MainMain.em" name="loginform" method="post" onsubmit="return check()">
   
  <div class="imgcontainer">
    <img src="./resources/images/main.jpg" alt="main" class="main">
  </div>
	
	<div class="btnCancel" style="background-color:#0086b3">
 	<font color="#FFFFFF" style="padding: 20px" ><b>
 	임직원 여러분 환영합니다.</b></font>
 	 </div>

  	<!-- 타이틀 : 회사명 -->
  	<%
  		String cNm;
  	
  		for(int i = 0; i< listCount; i++) {
  			SelDTO data = (SelDTO) list.get(i);
			cNm = data.getNm();
			if (cNm == null) {
				cNm = "";
			}
  		
  	%>
	<input type="hidden" name="comp_nm" value="<%=cNm %>" >
  	<%
		}
	%>	
	
  <div class="container"><!-- 회사코드 -->
  	<label><b>회사명</b></label>
  	<select name="company" id="combobox" class="w3-input"
  	style="width: 136px; font-size: 16px; display: inline-block;" >
  	<%				
  		String cd;
		String nm;

		for (int i = 0; i < listCount; i++) {
			SelDTO data = (SelDTO) list.get(i);
			cd = data.getCd();
			if (cd == null) {
				cd = "";
			}
			nm = data.getNm();
			if (nm == null) {
				nm = "";
		}
	%>
  		<option value="<%=cd%>" <%=i == 0 ? "selected" : " "%>><%=nm %></option>
  	<%
		}
	%>	
  	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <label><b>아이디</b></label>
    <input type="text" name="id" maxlength="20" placeholder="Enter Id" >
	&nbsp;
    <label><b>비밀번호</b></label>
    <input type="password" name="pw" maxlength=20 placeholder="Enter Password" >
	&nbsp;&nbsp;
    <button type="submit" style="font-weight: bold;" >로그인</button>
	
  </div>

</form>
</div>  
</body>
</html>