<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="spring.mybatis.gw.board.dto.BoardDTO"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*" %>
<%
String session_user_gubun = (String) request.getSession().getAttribute("gubun"); 
String session_empnm = (String) request.getSession().getAttribute("empnm");
	
	
	
	// 날짜
	Date date = new Date();
	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
	String strdate = simpleDate.format(date); 
	
/* 	if(session_user_gubun==null){
		session_user_gubun = "U";
	} */
%>
<html>
<head>
<title>[공지사항 게시판]</title>
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
<script type="text/javascript">


$(function(){
	$("#addFile").click(function(){
		var cnt = $("#fileaddcnt").val();
		cnt++;
		$("#fileaddcnt").val(cnt);
		var str = "";
		str = "<input name=\"uploadFile" + cnt + "\" type=\"file\"/> <br />";		
		$("#divFile").append(str);
	});
});

function addboard() { //함수     submit기능을 달아준것이다?
		
	var boardTitle = boardform.boardTitle.value;
	var boardContent = boardform.boardContent.value;

	//alert("1111"); 
	
	if (boardTitle.length == 0) {
		alert("제목을 입력하세요.");
		boardform.boardTitle.focus();
		return false;
	}
	if (boardContent.length == 0) {
		alert("내용을 입력하세요.");
		boardform.boardContent.focus();
		return false;
	}

	return boardform.submit();
		
	
}



</script>
</head>
<body class="w3-container w3-text-theme">

<!-- 게시판 등록 -->


<div class="w3-display-container " style="height:48px;">
	<div class="w3-display-left w3-padding-small " style="font-size:200%">공지사항 게시글 등록</div>	
</div>

	<form name="boardform" action="./BoardAddAction.bo" method="post" enctype="multipart/form-data"   >
	<input id="fileaddcnt" name="fileaddcnt" type="hidden" value="0" />
		<!-- boardform 이름으로 form안에 있는 것을 부를거다-->
		<!--multipart/form-data로 해야 파일까지 전송한다.  -->
		<!-- application/x-www-form-urlencoded : 이것이 디폴트이다. -->
		
	<table cellpadding="0" cellspacing="10px" class="w3-container w3-card-4">
		<tr>
			<td><br></td>
		</tr>
	
		<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right"><b>제 목&nbsp;&nbsp;</b></td>
			<td>
				<input name="boardTitle" type="text" size="50" maxlength="100" value=""  placeholder="이곳에 제목을 입력하세요." />
			</td>
		</tr>
		<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right"><b>날 짜&nbsp;&nbsp;</b></td>
			<td><%=strdate %></td>
		</tr>
		<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right"><b>작성자&nbsp;&nbsp;</b></td>
			<td><%= session_empnm %></td>
		</tr>
		<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right"><b>공지 구분&nbsp;&nbsp;</b></td>
			<td>
				<select name="gubun" id="combobox" class="w3-input" style="width: 150px; font-weight:bold; height: 32px; font-size: 13px; display: inline-block; padding:3px; ">
				<%
				if (session_user_gubun.equals("M")) {
					out.println("<option value=\"0\">시스템공지</option>");			
				} else {
					out.println("<option value=\"1\">전체공지</option>");
					out.println("<option value=\"2\">팀공지</option>");
				}
				%>
				</select>
			</td>
		</tr>
		
		<tr>
			<td style="font-family:돋음; font-size:12" class="w3-text-blue w3-large w3-right" ><b>내 용&nbsp;&nbsp;</b></td>
			<td>
				<textarea name="boardContent" cols="67" rows="15" ></textarea>
			</td>
		</tr>
		<tr>
			<td style="font-family:돋음; font-size:12" class="w3-text-blue w3-large w3-right"><b>파일 첨부&nbsp;&nbsp;</b></td>
			<td>
				<div id="divFile">
					
					<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small "
						id="addFile" >&nbsp;추가&nbsp;
					</button><br/>
					<input name="uploadFile0" type="file"/>
					<br />
						
				</div>				
			</td>
		</tr>
		
		<tr align="center" valign="middle">
			
			<td colspan="5">
				<font size=2>					
					<a href="javascript:history.go(-1)" class="w3-btn w3-blue w3-border w3-round">뒤로</a>&nbsp;&nbsp;
					<a href="javascript:addboard()" class="w3-btn w3-blue w3-border w3-round">등록</a>&nbsp;&nbsp;					
				</font>
			</td>
		</tr>
		
		<tr>
			<td><br></td>
		</tr>
		
	</table>
	
	<br />
	
	<!-- <div>
		<a href="javascript:addboard()">[등록]</a>&nbsp;&nbsp;
		<a href="javascript:history.go(-1)">[뒤로]</a>
	</div> -->
	
</form>
<!-- 게시판 등록 -->

</body>
</html>