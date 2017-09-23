<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="spring.mybatis.gw.board.dto.BoardDTO"%>
<%@ page import="spring.mybatis.gw.share.dto.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>


<%
	String session_empnm = (String) request.getSession().getAttribute("empnm"); 
	

	request.setCharacterEncoding("utf-8");
	BoardDTO board = (BoardDTO)request.getAttribute("boarddata");
	int num = board.getNO();
	List<FileDetailDTO> fileDetail = (List)request.getAttribute("fileList");	// 파일 여러개 가져오는 부분
	int fileDetailCount = fileDetail.size();
	boolean isWriter = (Boolean) request.getAttribute("isWriter"); // 작성자 인지 아닌지
	
	// 날짜
	Date date = new Date();
	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
	String strdate = simpleDate.format(date); 
	
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>[공지사항 게시판]</title>
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">	
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<script type="text/javascript">
		function clickDelete(){
			
			if (confirm("기존 관련 데이터가 있을 수 있습니다 정말로 삭제 하시겠습니까?")) 
				{
					location.href = './BoardDeleteAction.bo?num=<%=num %>&FILE_M_ID=<%=board.getFILE_M_ID() %>'  
			        return true;
				}else{
				        //alert("선택된 데이터가 없습니다."); // error!! 검색어 text에서 엔터키를 치면 이 메시지가 계속 뜬다               
				return false;			
				}
		}
		
		
		/* var form = document.getElementById('listForm');
		if (confirm("기존 관련 데이터가 있을 수 있습니다 정말로 삭제 하시겠습니까?")) {
		        form.action = "./BoardList.bo";
		        form.submit();
		        return true;
		}else if(sum==0){
		        //alert("선택된 데이터가 없습니다."); // error!! 검색어 text에서 엔터키를 치면 이 메시지가 계속 뜬다               
		}
		return false; */

</script>
	
	
</head>

<body  class="w3-container w3-text-theme">
<!-- 게시판 상세보기 -->
<!-- <h4 style="font-weight: bold;" >MVC 게시판 상세보기</h4> -->
<div class="w3-display-container " style="height:48px;">
	<div class="w3-display-left w3-padding-small " style="font-size:200%">공지사항 게시글 상세 보기</div>	
</div>


<table cellpadding="0" cellspacing="10px" class="w3-container w3-card-4" >
	<tr align="center" valign="middle">
		<td colspan="5" ><br /></td>
	</tr>
	
	<tr>
		<td style="font-family:돋음; font-size:12" height="16" class="w3-text-blue w3-large w3-right">
			<b>제 목&nbsp;&nbsp;</b>
		</td>
		
		<td style="font-family:돋음; font-size:12"  >   <!-- class="w3-input w3-border w3-threequarter " -->
		&nbsp;<%=board.getSUBJECT()%> 
		</td>
	</tr>
	
	<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right" ><b>날 짜&nbsp;&nbsp;</b></td>
			<td>&nbsp;<%=strdate %></td>
	</tr>
	
	<tr>
			<td style="font-family: 돋음; font-size: 12" height="16" class="w3-text-blue w3-large w3-right" ><b>작성자&nbsp;&nbsp;</b></td>
			<td>&nbsp;<%= session_empnm %></td> 			
		</tr>
		
	<tr bgcolor="cccccc">  
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	
	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center" class="w3-text-blue w3-large w3-right" style="font-weight: bold;">내 용&nbsp;&nbsp;</div>
		</td>
		<td style="font-family:돋음; font-size:12">
			<table border=0 width=490 height=250 style="table-layout:fixed">
				<tr>
					<td valign=top style="font-family:돋음; font-size:12">
					&nbsp;<%=board.getCONTENT() %>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
	
	
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	
	<!-- 파일 가져오는 부분 -->	
	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center" class="w3-text-blue w3-large w3-right" style="font-weight: bold;">첨부파일&nbsp;&nbsp;</div>
		</td>
		<td style="font-family:돋음; font-size:12">
		
		
		<%if(board.getFILE_M_ID()>0){ 
				
			for( int i = 0; i < fileDetailCount; i++){	
			%>&nbsp;
			<a href="./BoardFileDown.bo?path=<%=  fileDetail.get(i).getFile_rnm()  %>&originalFileName=<%=fileDetail.get(i).getFile_nm()%>">				<!--  경로는 rnm 진짜이름으로 가져오기  -->
						<%=  fileDetail.get(i).getFile_nm()  %>        <!-- nm  -->
						<%String test= fileDetail.get(i).getFile_nm(); %>
						
			</a> <br/>
			<%} 
			}%>
		</td>
	</tr>
	
	

	 
	
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;"></td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" valign="middle" >
		<td colspan="5">
			<font size=2>
			
			<!-- // 삭제 버튼이 게시글 작성자에게만 보이게 하는 기능 구현 -->
			<%if( isWriter ){   %> 
				
						<a href="javascript:clickDelete()" class="w3-btn w3-blue w3-border w3-round"  >			
						삭제
						</a>&nbsp;&nbsp;
							
			<% }%>		
			
			<!-- //////////////////////////////////////////////////////////////////////////////////////////////////// -->
			
			
			
			
			<!-- // 수정 버튼이 게시글 작성자에게만 보이게 하는 기능 구현 -->					
			<%if( isWriter ){   %> 
						<a href="./BoardModify.bo?no=<%=board.getNO() %>"  class="w3-btn w3-blue w3-border w3-round">
						수정
						</a>&nbsp;&nbsp;
						
						
						<%-- <a href=".BoardDelete.bo?num=<%=board.getNO() %>">			
						[삭제]
						</a>&nbsp;&nbsp; --%>
			<% }%>			
				
			
			
			<!-- /////////////////////////////////////////////////////////////////////////////////////////////////// -->
			
			
			
			
			<a href="./BoardList.bo" class="w3-btn w3-blue w3-border w3-round">목록</a>&nbsp;&nbsp;
			</font>
		</td>
		
	</tr>
	<tr>
		<td>
	 			<br><br>
		</td>
	</tr>
</table>

<!-- 게시판 상세보기 -->
</body>
</html>