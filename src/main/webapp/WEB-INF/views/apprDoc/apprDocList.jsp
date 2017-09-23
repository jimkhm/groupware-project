<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="spring.mybatis.gw.apprdoc.dto.*" %>
<%@ page import="spring.mybatis.gw.apprdoc.dao.*" %>
<%@ page import="spring.mybatis.gw.apprdoc.dao.ApprStatusDAO" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import= "org.springframework.web.context.WebApplicationContext" %>

<%
	//request 영역에 저장된 데이터들을 꺼내고 있음
	//꺼내서 화면을 완성하기 위해서이다. 

	List<ApprListDTO> apprDocList=(List<ApprListDTO>)request.getAttribute("apprDoclist1"); //실제 데이터들은 ArrayList에 담아 뒀었음
	int listcount=((Integer)request.getAttribute("listcount1")).intValue();//데이터 개수 읽어와서 저장한 것
	int nowpage=((Integer)request.getAttribute("page1")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage1")).intValue();
	int startpage=((Integer)request.getAttribute("startpage1")).intValue();
	int endpage=((Integer)request.getAttribute("endpage1")).intValue();
	
	List apprDocList2=(List)request.getAttribute("apprDoclist2"); //실제 데이터들은 ArrayList에 담아 뒀었음
	int listcount2=((Integer)request.getAttribute("listcount2")).intValue();//데이터 개수 읽어와서 저장한 것
	int nowpage2=((Integer)request.getAttribute("page2")).intValue();
	int maxpage2=((Integer)request.getAttribute("maxpage2")).intValue();
	int startpage2=((Integer)request.getAttribute("startpage2")).intValue();
	int endpage2=((Integer)request.getAttribute("endpage2")).intValue();
	
	
	
	 WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) request).getSession().getServletContext());
	 ApprStatusDAO boardDao = (ApprStatusDAO)wac.getBean("apprStatusDAO");
	
	
	
	//ApprStatusDAO boardDao = new ApprStatusDAO();
	
	String emp_id = (String) session.getAttribute("id");
	String comp_cd = (String)session.getAttribute("company");
	String emp_no = (String) session.getAttribute("empno");
	
	String subApprTitle = "";
	String appr_title = "";
	
%>

<html>
<head>
	<title>결재 서류 리스트</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">	
<style type="text/css">
	.div1 {width : 1000px; align : left;}
	.p1 {text-align : center;}
</style>
<script type="text/javascript">
	function fullTitle2(obj, index){ /*onmouseover*/
		obj = eval("document.all.title2" + index + ".style");
		obj.display = "";
		obj = eval("document.all.subTitle2" + index + ".style");
		obj.display = "none";
	}
	function defaultTitle2(obj, index){/*onmouseout*/
		obj = eval("document.all.title2" + index + ".style");
		obj.display = "none";
		obj = eval("document.all.subTitle2" + index + ".style");
		obj.display = "";
	}
	
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
<body  class ="w3-container">
<div class="div1">
<br>


<!-- 게시판 리스트 -->
<div class="w3-center"  >
<h3  class="w3-left" colspan="4"><b>결재 서류 리스트</b></h3>
		<h5 class="w3-right">
			결재 서류 개수 : ${listcount2 }
		</h5>
		
<table align="center" class="w3-table w3-striped w3-bordered w3-card-4" >
<%
if(listcount2 > 0){//데이터가 하나도 저장하지 않았을 경우 걸러냄
%>
	

	<tr class="w3-blue">
		<th class="w3-center" style="width:130px">결재서류 번호</th>
		<th class="w3-center" style="width:80px">결재분류</th>
		<th class="w3-center">제목</th>
		<th class="w3-center" style="width:80px">기안자</th>
		<th class="w3-center" style="width:80px">기안일</th>
		<th class="w3-center" style="width:80px">마감일</th>
		<th class="w3-center" style="width:70px">상태</th>		
	</tr>
	
	<%
		//ApprStatusDAO boardDao = new ApprStatusDAO();
	
		for(int i=0; i<apprDocList2.size();i++){
			ApprListDTO bl2=(ApprListDTO)apprDocList2.get(i);//저장된 순서를 그대로 유지하고 있어서 get이라는 메소드가 유효한 ArrayList			
			//System.out.println("결재서류번호 실험" +bl2.getAppr_no());			
			int appr_max = boardDao.getApprMax(bl2.getAppr_no());//토털 결재 라인 마지막 순서 가져오기
			int appr_me = boardDao.getApprMe(emp_no, bl2.getAppr_no());//토털 결재 라인 내 순서 가져오기
			String my_line =   boardDao.getApprMyLine(emp_no, bl2.getAppr_no());//내 결재라인 가져오기(결재자/합의자/참조자/시행자)
			int yn = boardDao.getApprYN( bl2.getAppr_no());//거부/반려가 있는지 살펴보기 
			int history_max = boardDao.getApprHistoryMax(bl2.getAppr_no());//결재 서류 이력 중 최고 결재 순서 가져오기
			int myHistory = boardDao.getMyApprHistory(emp_no, comp_cd, bl2.getAppr_no());//결재 서류 이력 중 내 이력이 있는지 체크하기
			String mySatus = boardDao.getMyHistoryStatus(emp_no, comp_cd, bl2.getAppr_no());//결재 서류 이력 중 내 이력의 상태 체크하기
	%>
	
	<% 
			appr_title = bl2.getAppr_title();
			
			if(bl2.getAppr_title().equals(bl2.getSubApprTitle())){
				subApprTitle = bl2.getSubApprTitle();
			}else{
				String temp1 = appr_title.substring(0, bl2.getSubApprTitle().length());
				String temp2 = appr_title.substring(bl2.getSubApprTitle().length(), appr_title.length());
				appr_title = temp1 + "<br/>" + temp2;
				subApprTitle = bl2.getSubApprTitle() + "...";
				
			}
		
	
	%>
	
	
	<tr >
		<td class="w3-center">
			<%= bl2.getAppr_no() %>
			<%-- <%=bl.getBOARD_NUM()%><!--jsp가 해당 값을 읽고 값을 이 자리에 위치 시킴  --> --%>
		</td>
		<td class="w3-center">
			<div>
				<%if(my_line.equals("D")){ %>
						<%="참조" %>
				<%}else if(my_line.equals("C")){ %>
						<%="시행" %>
				<%}else if(myHistory >= 1){ %>
						<%=mySatus +"(C)" %>
				<%}else if(history_max== 0) {%>
								<%if(appr_me == 1 ){  %>
								 		<%="미결" %>
								<%}else if(appr_me > 1){ %>
										<%="예결" %>
								<%}%>
				<%}else if(appr_me == history_max+1) {%>
						<%="미결" %>
				<%}else if(appr_me > history_max+1){%>
						<%="예결" %>
				<%}else if(appr_me < history_max){ %>
						<%="후결" %>
				<%} %>				
			</div>
		</td>
		
		<td class="w3-left"
		onmouseover="fullTitle2(this, <%=i %>);" onmouseout="defaultTitle2(this, <%=i %>);"
		>
			<div>			
			<a href="./ApprDetail.appr?appr_no=<%= bl2.getAppr_no() %>">
				<span id="title2<%=i %>" style="display:none;"><%= appr_title %></span>			
				<span id="subTitle2<%=i %>" ><%=subApprTitle%></span>				
			</a>
			</div>			
		</td>
		
		<td class="w3-center"><%=bl2.getEmp_nm() %>	</td>
		<td class="w3-center"><%=bl2.getInsert_dt() %></td>		
		<td class="w3-center"><%=bl2.getAppr_date_final() %></td>
		<td class="w3-center">
			<div>			
			<%if(history_max==0){ %>
					<%="상신" %>
				<%}else if(yn > 0){%> 
					<%="중지" %>					
				<% }else if(yn == 0){
							if(appr_max == history_max ){%>
							<%="종결" %>																	
							<% }else if(appr_max > history_max ){%>
							<%="진행중" %>							
							<%}					
				}				
				%>			
			</div>
		</td>		
	</tr>
	<%} %>	
	<%
    }
else
	{
	%>
	<tr>		
		<td class="w3-center" colspan="7">	등록된 글이 없습니다.</td>
	</tr>
	<%
	}
	%> 	
</table>
<br>
	<div  align="center" class="w3-center">
			<%if(nowpage2<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="./ApprList.appr?page2=<%=nowpage2-1 %>">[이전]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage2;a<=endpage2;a++){
				if(a==nowpage2){%>
				[<%=a %>]
				<%}else{ %>
				<a href="./ApprList.appr?page2=<%=a %>">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage2>=maxpage2){ %>
			[다음]
			<%}else{ %>
			<a href="./ApprList.appr?page2=<%=nowpage2+1 %>">[다음]</a>
			<%} %>
		</div>

<br>


<h3 class="w3-left" ><b>상신 문서</b></h3>
<h5 class="w3-right">상신문서 개수 : ${listcount1 }		</h5>
<table align="center" class="w3-table w3-striped w3-bordered w3-card-4">
<%
if(listcount > 0){//데이터가 하나도 저장하지 않았을 경우 걸러냄
%>
	<tr class="w3-blue">		
		<th class="w3-center" style="width:130px">결재서류 번호</th>		
		<th class="w3-center">제목</th>
		<th class="w3-center" style="width:80px">기안자</th>
		<th class="w3-center" style="width:80px">기안일</th>
		<th class="w3-center" style="width:80px">마감일</th>
		<th class="w3-center" style="width:70px">상태</th>		
	</tr>	
		
		
	<%
		//ApprStatusDAO boardDao = new ApprStatusDAO();
	
		for(int i=0;i<apprDocList.size();i++){
			ApprListDTO bl=(ApprListDTO)apprDocList.get(i);//저장된 순서를 그대로 유지하고 있어서 get이라는 메소드가 유효한 ArrayList			
			int appr_max = boardDao.getApprMax(bl.getAppr_no());
			int appr_me = boardDao.getApprMe(emp_no, bl.getAppr_no());
			String my_line =   boardDao.getApprMyLine(emp_no, bl.getAppr_no());	
			int yn = boardDao.getApprYN( bl.getAppr_no());
			int history_max = boardDao.getApprHistoryMax(bl.getAppr_no());
			
	%>
	<% 
			appr_title = bl.getAppr_title();
			
			if(bl.getAppr_title().equals(bl.getSubApprTitle())){
				subApprTitle = bl.getSubApprTitle();
			}else{
				String temp1 = appr_title.substring(0, bl.getSubApprTitle().length());
				String temp2 = appr_title.substring(bl.getSubApprTitle().length(), appr_title.length());
				appr_title = temp1 + "<br/>" + temp2;
				subApprTitle = bl.getSubApprTitle() + "...";
				
			}
	
	%>
	
	
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='F8F8F8'"
		onmouseout="this.style.backgroundColor=''">
		<td class="w3-center">
			<%= bl.getAppr_no() %>
			
		</td>
		
		<td class="w3-left"
		onmouseover="fullTitle(this, <%=i %>);" onmouseout="defaultTitle(this, <%=i %>);"
		>
			<div>			
			<a href="./ApprDetail.appr?appr_no=<%= bl.getAppr_no() %>">
				<span id="title<%=i %>" style="display:none;"><%= appr_title %></span>			
				<span id="subTitle<%=i %>" ><%=subApprTitle%></span>				
			</a>
			</div>			
		</td>
		
		<td >
			<div align="center"><%=bl.getEmp_nm() %> </div>
		</td>
		<td >
			<div align="center"><%=bl.getInsert_dt() %></div>
		</td>
		<td >
			<div align="center"><%=bl.getAppr_date_final() %>
			
			
			</div>
		</td>
		<td >
			<div align="center">
				<%if(history_max==0){ %>
					<%="상신" %>
				<%}else if(yn > 0){%> 
					<%="중지" %>					
				<% }else if(yn == 0){
							if(appr_max == history_max ){%>
							<%="종결" %>																	
							<% }else if(appr_max > history_max ){%>
							<%="진행중" %>							
							<%}					
				}				
				%> 
			
			</div>
		</td>		
	</tr>
	<%} %>
	
	<%
    }
	else
	{
	%>
	<tr align="center" >
		<td colspan="4"><b>결재 서류 리스트</b></td>
		<td align=right>
			<font size=2>등록된 글이 없습니다.</font>
		</td>
	</tr>
	<%
	}
	%>
	
	</table>
	<br />
		<div  align="center" class="w3-center">
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="./ApprList.appr?page=<%=nowpage-1 %>">[이전]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="./ApprList.appr?page=<%=a %>">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="./ApprList.appr?page=<%=nowpage+1 %>">[다음]</a>
			<%} %>
		</div>
		
	<div class="w3-right">
	   		<h4><a href="/gw/ApprWriter.appr">[결재서류 상신]</a></h4>
	   	</div>
	   	<br>
	
	</div>
</div>
</body>
</html>
	

