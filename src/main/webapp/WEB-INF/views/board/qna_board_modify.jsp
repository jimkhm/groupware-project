<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="spring.mybatis.gw.board.dto.BoardDTO"%>
<%@ page import="spring.mybatis.gw.share.dto.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*" %>
<%
String session_user_gubun = (String) request.getSession().getAttribute("gubun");
String session_empnm = (String) request.getSession().getAttribute("empnm"); 

	BoardDTO board = (BoardDTO)request.getAttribute("boarddata");
	List<FileDetailDTO> fileDetail = (List)request.getAttribute("fileList");	// 파일 여러개 가져오는 부분
	int fileDetailCount = fileDetail.size();

// 날짜
Date date = new Date();
SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
String strdate = simpleDate.format(date); 


%>

<html>
<head>
	<title>[공지사항 게시판]</title>
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">	
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<style>
.viewoff {
	display : none;
}
.viewon {
	display : ;
}
</style>
<script src="./resources/js/jquery-3.1.0.js"></script>
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
	
function modifyboard(){
	modifyform.submit();
}
function delFile(idx){
	var delFileYN = document.getElementById("delFileYN" + idx);
	delFileYN.value = "Y";
	
	
	
	
	var divSavedFile = document.getElementById("divSavedFile" + idx);	
	 if (divSavedFile.className.indexOf("viewoff") == -1) {
		 divSavedFile.className += " viewoff";
	 }	
}
</script>
</head>

<body class="w3-container w3-text-theme">
<!-- 게시판 수정 -->
<form name="modifyform" id="modifyform" action="./BoardModifyAction.bo"  method="post" enctype="multipart/form-data">
<input type="hidden" name="no" value=<%=board.getNO() %>>
<input type="hidden" name="delFileCnt" value="<%=fileDetailCount %>" />
<input type="hidden" name="FILE_M_ID" value="<%= board.getFILE_M_ID() %>"/>

	
	
	<div class="w3-display-container " style="height:48px;">
		<div class="w3-display-left w3-padding-small " style="font-size:200%">공지사항 게시글 수정</div>	
	</div>
	
	
	<!-- <p><h4 style="font-weight: bold;" > &nbsp; MVC 게시판 수정</h4></p> -->
    <table cellpadding="0" cellspacing="10px" class="w3-container w3-card-4">
    	<tr>
			<td><br></td>
		</tr>
	
	
		<tr>
			<td style="font-family:돋음; font-size:12">
				<div align="center" class="w3-text-blue w3-large w3-right"><b>제 목&nbsp;&nbsp;</b></div>
			</td>
			<td>
				<input name="SUBJECT" size="50" maxlength="100" 
					value="<%=board.getSUBJECT()%>">
			</td>
		</tr>
	
		<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right">&nbsp;&nbsp;<b> 날 짜&nbsp;&nbsp;</b></td>
			<td>&nbsp;<%=strdate %></td>
		</tr>
		
		
		<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right">&nbsp;&nbsp;<b> 작성자&nbsp;&nbsp;</b></td>
			<td>&nbsp;<%= session_empnm %></td> 
		</tr>
		
		<%-- <tr>
			<td style="font-family: 돋음; font-size: 12" height="16">공지구분</td>
			<td>&nbsp;<%= session_user_gubun %></td>
		</tr>	 --%>
		
		
		<tr>
			<td style="font-family:돋음; font-size:12">
				<div align="center" class="w3-text-blue w3-large w3-right"><b>내 용&nbsp;&nbsp;</b></div>
			</td>
			<td>
				<textarea name="CONTENT" cols="67" rows="15"><%=board.getCONTENT() %></textarea>
			</td>
		</tr>
	
		<tr>
				<td style="font-family:돋음; font-size:12" class="w3-text-blue w3-large w3-right"><b>파일 첨부&nbsp;&nbsp;</b></td>
				<td>
				
					<div id="divSavedFile">
					<% if(board.getFILE_M_ID()>0){ 
							for( int i = 0; i < fileDetailCount; i++){	
						%>
							<div id="divSavedFile<%=i %>">
							<a href="./BoardFileDown.bo?path=<%=  fileDetail.get(i).getFile_rnm()  %>">				<!--  경로는 rnm 진짜이름으로 가져오기  -->
							<%=  fileDetail.get(i).getFile_nm()  %></a>
							<span class="w3-right">
							<i id="delFileIcon" onclick="return delFile('<%=i %>');" class="fa fa-times-circle" style="font-size:24px"></i>
							<input id="delFile<%=i %>" name="delFile<%=i %>" type="hidden" value="<%=  fileDetail.get(i).getFile_d_id()  %>"/>
							<input id="delFileYN<%=i %>" name="delFileYN<%=i %>" type="hidden" value="N"/>
							</span>
							</div>
					<%} }%>
					</div>
				
					<div id="divFile">
						<br>
						<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small"
							id="addFile" >&nbsp;추가&nbsp;
						</button><br />
						<input name="uploadFile0" type="file"/><br/>
						
					</div>
					
				</td>
			</tr>
	
	
	
	
	<%-- <%if(!(board.getBOARD_FILE()==null)){ %>
	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center">파일 첨부</div>
		</td>
		<td>
			&nbsp;&nbsp;<%=board.getBOARD_FILE() %>
		</td>
	</tr>
	<%} %> --%>
	
	<!-- <tr>
		<td height="16" style="font-family:돋음; font-size:12">
			<div align="center">비밀번호</div>
		</td>
		<td>
			<input name="BOARD_PASS" type="password">
		</td>
	</tr> -->	
	
	
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" valign="middle">
		<td colspan="5">
			<font size=2>			
				<a href="javascript:history.go(-1)" class="w3-btn w3-blue w3-border w3-round">뒤로</a>&nbsp;&nbsp;
				<a href="javascript:modifyboard()" class="w3-btn w3-blue w3-border w3-round">수정</a>&nbsp;&nbsp;
			</font>
		</td>
	</tr>
	
	<tr>
			<td><br></td>
	</tr>
	
</table>
</form>
<!-- 게시판 수정 -->
</body>
</html>