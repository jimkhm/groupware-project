<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="spring.mybatis.gw.board.dto.BoardDTO"%>

<%
	List boardList=(List)request.getAttribute("boardlist");
	int listcount=((Integer)request.getAttribute("listcount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
	//int startrow = ((Integer)request.getAttribute("startrow")).intValue();
	//int endrow = ((Integer)request.getAttribute("endrow")).intValue();
	//System.out.println("qna_board_list.jsp : 확인");
	//System.out.println("페이지를 확인 : "+listcount );
	//System.out.println("페이지를 확인 : "+page );
	//System.out.println("페이지를 확인 : "+nowpage);
	//System.out.println("페이지를 확인 : "+startpage);
	//System.out.println("페이지를 확인 : "+endpage);
	//System.out.println("페이지를 확인 : "+boardList);
	//System.out.println("row 확인 : "+startrow+endrow );
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[공지사항 게시판]</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<style type="text/css">
	.div1 {width : 1000px; align : left;}
	.p1 {text-align : center;}
</style>

<script type="text/javascript">
function fullTitle(obj, index){ /*onmouseover*/
	obj = eval("document.all.title" + index + ".style");
	obj.display = "";
	obj = eval("document.all.subTitle" + index + ".style");
	obj.display = "none";
}
function defaultTitle(obj, index){/*onmouseout*/
	obj = eval("document.all.title" + index + ".style");
	obj.display = "none";
	obj = eval("document.all.subTitle" + index + ".style");
	obj.display = "";
}
</script>

</head>
<body class="w3-container w3-text-theme">
<br />
<div class="div1">

<div class="w3-display-container " style="height:48px;">
	<div class="w3-display-left w3-padding-small " style="font-size:200%">공지사항 게시판</div>
	<div class="w3-display-bottomright w3-padding-small" ><% out.print("  총게시물 수 : " + listcount); %></div>
</div>

<table class="w3-table w3-striped w3-bordered w3-card-4">
<thead>
<tr class="w3-theme">
			<th class="w3-center" style="width:70px">번호</th>
			<th class="w3-center" style="width:70px">구분</th>
			<th class="w3-center" >제목</th>
			<th class="w3-center" style="width:100px">이름</th>
			<th class="w3-center" style="width:108px">날짜</th>
			<th class="w3-center" style="width:70px">조회수</th>			
</tr>
</thead>

<%
	 int boardListCount = 0;
	String board_subject_sub = "";
	String board_subject = "";
	
	if ( boardList != null){
		boardListCount = boardList.size();
	}
	
	if(boardListCount > 0){
		
		for(int i = 0, ii = boardListCount; i < boardListCount;  i++, ii--) {
			
			BoardDTO bl=(BoardDTO)boardList.get(i);
			
			board_subject = bl.getSUBJECT();
			
			if(bl.getSUBJECT().equals(bl.getSUB_SUBJECT())){
				board_subject_sub = bl.getSUB_SUBJECT();
			} else {
				String temp1 = board_subject.substring(0, bl.getSUB_SUBJECT().length());
				String temp2 = board_subject.substring(bl.getSUB_SUBJECT().length(), board_subject.length());
				board_subject = temp1 + "<br />" + temp2;
				board_subject_sub = bl.getSUB_SUBJECT() +  " ...";
			}
			
			java.text.SimpleDateFormat formmat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
			String insertDt = formmat.format(bl.getINSERT_DT());
			
	%>
	
<tr>
	<td class="w3-center" ><%= ii %></td>
	<td class="w3-center" >
	<%
		if (bl.getGUBUN().equals("0")) {
			out.print ("시스템");
		} else if (bl.getGUBUN().equals("1")) {
			out.print ("전체");
		} else if (bl.getGUBUN().equals("2")) {
			out.print ("팀");
		}
	%>
	</td>
	<td class="w3-navbar w3-tooltip"
	onmouseover="fullTitle(this, <%=i %>);" onmouseout="defaultTitle(this, <%=i %>);"
	>
	<a href="./BoardDetailAction.bo?no=<%=bl.getNO()%>"
	 class="w3-hover-text-white w3-hover-pink">
				<span id="title<%=i %>" style="display:none;"><%= board_subject %></span>
				<span id="subTitle<%=i %>" ><%= board_subject_sub %></span>
	</a>
	</td>
	<td class="w3-center" ><%=bl.getEMP_NM() %></td>
	<td class="w3-center" ><%= insertDt %></td>
	<td class="w3-center" ><%=bl.getREADCOUNT() %></td>
</tr>
	
<% }} %>
<!-- 데이터가 없을 때 -->
<% if (boardListCount==0) { %>
<tr>
	<td class="w3-center" colspan="6">등록된 글이 없습니다.</td>
</tr>
<% } %>
</table>
<br />

	<div class="w3-center">
		
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="./BoardList.bo?page=<%=nowpage-1 %>">[이전]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="./BoardList.bo?page=<%=a %>">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="./BoardList.bo?page=<%=nowpage+1 %>">[다음]</a>
			<%} %>
		
	</div>
	
	<div class="w3-btn w3-blue w3-border w3-round w3-right">
	<a href="./BoardWrite.bo">글쓰기</a>
	</div>

</div>

</body>
</html>