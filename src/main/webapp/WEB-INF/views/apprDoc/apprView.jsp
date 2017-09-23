<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="spring.mybatis.gw.apprdoc.dao.*" %>
<%@ page import="spring.mybatis.gw.share.dto.*" %>
<%@ page import="spring.mybatis.gw.apprdoc.dto.*" %>
<%@ page import="java.util.*"%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
​
<%
	//request 영역에 저장된 데이터를 꺼냄	
	ApprDetailDTO1 apprDoc = (ApprDetailDTO1)request.getAttribute("apprDoc");
	List<ApprDetailDTO2> apprStepList = (List)request.getAttribute("apprStepList");
	
	FileMasterDTO fileMasterDto = (FileMasterDTO)request.getAttribute("fileMasterDto");
	List<FileDetailDTO> fileDetaillist = (List)request.getAttribute("fileDetaillist");
	
	String emp_no = (String)session.getAttribute("empno");
	String comp_cd  = (String)session.getAttribute("company");
	String emp_id = (String)session.getAttribute("id");
	
	int myHistory = (int)request.getAttribute("myHistory");
	int apprYN = (int)request.getAttribute("apprYN");
	int historyMax = (int)request.getAttribute("historyMax");
	int apprMe = (int)request.getAttribute("apprMe");
	String myLine = (String)request.getAttribute("myLine");	
	
%>

<html>
<head>
	<title>결재서류 상세화면</title> 
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
	<script src="./resouces/js/jquery-3.1.0.js"></script>
	<script type="text/javascript">
	function addboard(i){		
		switch(i){
		
		case 1:/* 기결 */
			document.getElementById("status").innerHTML = "<input type ='hidden' name='status' value = 'A'>";
			document.getElementById("appr").submit();
			break;
			
		case 2:/* 예결 */
			document.getElementById("status").innerHTML = "<input type ='hidden' name = 'status' value = 'B'> ";
			document.getElementById("appr").submit();
			break;
			
		case 3: /* 후결  */
			document.getElementById("status").innerHTML = "<input type = 'hidden' name = 'status' value = 'C' >";
			document.getElementById("appr").submit();
			break;
				
		case 4:/* 반려 */
			document.getElementById("status").innerHTML = "<input type ='hidden' name = 'status' value = 'D'>";
			document.getElementById("appr").submit(); 
			break;
			
		case 5:/* 합의 */
			document.getElementById("status").innerHTML = "<input type ='hidden' name = 'status' value = 'E'>";
			document.getElementById("appr").submit(); 
			break;
		
		case 6:/* 거부 */
			document.getElementById("status").innerHTML = "<input type ='hidden' name = 'status' value = 'F'>";
			document.getElementById("appr").submit(); 
			break;	
			
		}
	
	}
	
	function getHistoryPop(apprNo){
		
		var url = "./GetHistoryAction.appr?apprNo="+apprNo;
		window.open(url, "CLIENT_WINDOW", "width =730, height=600, toolbar=no, menubar=no, location=no, scrollbars= yes, status=no, resizable=no, fullscreen=no, channelmode=no, directories=no;");
		return false;
	}
	
	
		
	function removeCheck(apprNo) {
		
		 if (confirm("정말 삭제하시겠습니까??") == true){    //확인
			 location.href="./ApprDeleteAction.appr?appr_no="+apprNo;
		}else{   //취소
			     return false;
		}
	}
	
	function modifyCheck(apprNo) {		 
		location.href="./ApprModifyView.appr?appr_no="+apprNo;		
	}
	
	function listCheck(){		 
		location.href="./ApprList.appr" 		
	}

	
	
	</script>
</head>
<body class="w3-container" >
<div >
<h2>결재 서류</h2>
<form id="appr" action="./ApprHistoryAction.appr" method="post"
class="w3-container w3-card-4" style="width:750px;">
		
		<!-- 이것은 자동이동을 막기위함이다. -->
		<div style="display:none">
			<input type="submit" onclick="return false;" />
			<input type="text"/>
		</div>
		

		<div><p id="status"></p></div>
		
		
		<div class="w3-right">
		<!--합의/미결 버튼  -->
		<span>	
		<%if(myLine != null){ %>		
			<%if(myLine.equals("A") || myLine.equals("B")){ %>	
				<%if(myHistory ==0){ %>	
					<%if(apprYN == 0){ %>					
						<%if(apprMe == historyMax+1 ){ %>
							<%if(myLine.equals("B")){ %>
							<input  type="button" onclick="javascript:addboard(5);" value="합의" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%}else{ %>
		<input  type="button" onclick="javascript:addboard(1);" value="미결" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%} %>		
						<%} %>
					<%} %>
				<%} %>
			<%} %>
		<%} %>
		</span>
		
		<!--합의/예결 버튼  -->
		<span>
		<%if(myLine != null){ %>	
			<%if(myLine.equals("A") || myLine.equals("B")){ %>	
				<%if(myHistory ==0){ %>	
					<%if(apprYN == 0){ %>
						<%if(apprMe > historyMax+1 ){ %>
							<%if(myLine.equals("B")){ %>
							<input  type="button" onclick="javascript:addboard(5);" value="합의" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%}else{ %>
		<input  type="button" onclick="javascript:addboard(2);" value="예결"  class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%} %>		
						<%} %>
					<%} %>
				<%} %>
			<%} %>
		<%} %>
		</span>
		
		<!--합의/후결 버튼  -->
		<span>
		<%if(myLine != null){ %>	
			<%if(myLine.equals("A") || myLine.equals("B")){ %>	
				<%if(myHistory ==0){ %>	
					<%if(apprYN == 0){ %>
						<%if(apprMe < historyMax){ %>
							<%if(myLine.equals("B")){ %>
							<input  type="button" onclick="javascript:addboard(5);" value="합의" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							 <%}else{ %>
		<input type="button" onclick="javascript:addboard(3);" value="후결" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%} %>		
						<%} %>
					<%} %>
				<%} %>
			<%} %>
		<%} %>
		</span>
		
		<!--반려/거부 버튼  -->
		<span>
		<%if(myLine != null){ %>	
			<%if(myLine.equals("A") || myLine.equals("B")){ %>	
				<%if(myHistory ==0){ %>	
					<%if(apprYN == 0){ %>
						<%if(apprMe > historyMax ){ %>	
							<%if(myLine.equals("B")){ %>
							<input  type="button" onclick="javascript:addboard(6);" value="거부" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%}else{ %>			
		<input   type="button" onclick="javascript:addboard(4);" value="반려" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
							<%} %>		
						<%} %>
					<%} %>
				<%} %>
			<%} %>
		<%} %>
		</span>
		
		<!--결재 이력 버튼  -->
		<span>	
			<input type="button"  onclick="javascript:getHistoryPop(<%=apprDoc.getAppr_no() %>);" value="결재이력" class="w3-btn w3-blue w3-border w3-round " />&nbsp;										
		</span>
				
		&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	
	
			
			<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">				
				<input  type="hidden" name="appr_no" value="<%=apprDoc.getAppr_no() %>">
				<input name="comp_cd" type="hidden"  value="<%=comp_cd %>" >			
			</div>			
					
			<div >
				<label class="w3-text-blue w3-large"><b>제 목</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
					<input name="apprTitle" id="appr_title" class="w3-input w3-border w3-threequarter " type="text" 
					value="<%=apprDoc.getAppr_title()%>" readonly /> 				
				</div>
			</div>
							 
			<div>
				<label class="w3-text-blue w3-large"><b>내 용</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
					<textarea name="apprContent"   cols="60" rows="8" class="w3-input w3-border w3-threequarter "
					readonly><%=apprDoc.getAppr_content() %></textarea>
				</div>
			</div>
			<br>
								
			<div class="w3-row">		
				<div class="w3-col s4 w3-center">
					<label class="w3-text-blue w3-large"><b>상신일</b></label><br />
				 	<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
				 		<span class="w3-large" >
				 		<%=apprDoc.getInsert_dt() %>						
						</span>
					</div>
				</div>
				
				<div class="w3-col s4 w3-center">
		
					<label class="w3-text-blue w3-large"><b>결재일</b></label><br />
			 		<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
			 			<span class="w3-large" >
						<%=apprDoc.getAppr_date_appr()%>
						</span>
					</div>
				</div>
				
				<div class="w3-col s4 w3-center">
		
					<label class="w3-text-blue w3-large"><b>마감일</b></label><br />
				 	<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
				 		<span class="w3-large" >
						<%=apprDoc.getAppr_date_final()%>
						</span>
					</div>				
				</div>
				
			</div>
			<br/>
	
			<div >			
				<label class="w3-text-blue w3-large"><b>기안자</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-top">
					<span class="w3-large"><%=apprDoc.getEmp_nm()%></span>
				</div>
				<input type="hidden" name="emp_no"  value="<%=emp_no %>" >
				<input type="hidden" name="emp_id"  value="<%=emp_id %>" >				
			</div>		
	
			<div>
					<label class="w3-text-blue w3-large"><b>결재자</b></label><br />
					<div  id= "apprEmp" class="w3-container w3-margin-left w3-margin-right w3-margin-4 w3-large">
					<%if (apprStepList.size() != 0) {%>
						<%for (int i = 0; i < apprStepList.size(); i++) {
									if (apprStepList.get(i).getAppr_line().equals("A")) {%>
										<%=apprStepList.get(i).getEmp_nm() +"("+apprStepList.get(i).getEmp_no()+")" +"    "%><!--부서이름 추가  -->
								<%}%>
						<%}%>
					<%}%>
				</div>	
			</div>
			<br/>
			
			<div >		
				<label class="w3-text-blue w3-large"><b>합의자</b></label><br />
				<div id= "agreeEmp"  class="w3-container w3-margin-left w3-margin-right w3-margin-4 w3-large">
					<%if(apprStepList.size() != 0){ %>			 
							<%for(int i =0 ; i < apprStepList.size(); i++){  
								if(apprStepList.get(i).getAppr_line().equals("B")){%>			
							<%= apprStepList.get(i).getEmp_nm()+"("+apprStepList.get(i).getEmp_no()+")"  + "    "%>							
								<%}%>
						<%}%>
					<%} %>	
				</div>
			</div>
			<br/>
			
			<div >
				<label class="w3-text-blue w3-large"><b>시행자</b></label><br />
				<div id="enforceEmp" class="w3-container w3-margin-left w3-margin-right w3-margin-4 w3-large">
				<%if(apprStepList.size() != 0){ %>				 
					<%for(int i =0 ; i < apprStepList.size(); i++){  
						if(apprStepList.get(i).getAppr_line().equals("C")){%>			
					<%= apprStepList.get(i).getEmp_nm()+"("+apprStepList.get(i).getEmp_no()+")"  + "    "%>					
						<%}%>				
					<%}%>
				<%} %>				
				</div>			
			</div>
			<br/>
			
			<div >
			<label class="w3-text-blue w3-large"><b>수신참조자</b></label><br />
				<div id ="referEmp" class="w3-container w3-margin-left w3-margin-right w3-margin-4 w3-large">
					<%if(apprStepList.size() != 0){ %>				 
						<%for(int i =0 ; i < apprStepList.size(); i++){  
								if(apprStepList.get(i).getAppr_line().equals("D")){%>			
							<%= apprStepList.get(i).getEmp_nm()+"("+apprStepList.get(i).getEmp_no()+")"  + "    "%>					
								<%}%>
						<%}%>
					<%} %>	
				</div>
			</div>
			<br/>

 
			<div>
				<label class="w3-text-blue w3-large"><b>첨부 파일</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">					
						<%if(apprDoc.getFile_m_id() != 0){ %>
							<%for(int i=0 ; i < fileDetaillist.size(); i++){ %>								
						<%-- 	<a href="./boardUpload/<%=fileDetaillist.get(i).getFile_nm()%>">
								<%=fileDetaillist.get(i).getFile_rnm() %>
							</a><br /> --%>
							<span onclick="location.href='./apprFileDown.appr?path=<%=fileDetaillist.get(i).getFile_rnm()%>&originalFileName=<%=fileDetaillist.get(i).getFile_nm()%>'"><%=fileDetaillist.get(i).getFile_nm() %></span>
							<br/>
							<%} %>								
						<%} %>	
				</div>
			</div>
			<br />

			<div class="w3-center w3-large">			
			<!--답변이 눌러지면 파라미터와 명령어를 통해 요청  -->
			<%-- <a href="./BoardReplyView.bo?num=<%=board.getBOARD_NUM() %>">
			[답변]
			</a>&nbsp;&nbsp;
			<a href="./BoardModify.bo?num=<%=board.getBOARD_NUM() %>">
			[수정]
			</a>&nbsp;&nbsp; --%>
			
			<%System.out.println("emp_no 체크"+emp_no); %>
			<%System.out.println("getEmp_no()() 체크"+apprDoc.getEmp_no()); %>
			<%if(historyMax == 0){ %>
				<%if(emp_no.equals(apprDoc.getEmp_no())){ %>
			<input type="button" onclick="javascript:modifyCheck(<%=apprDoc.getAppr_no() %>);" value="수정" class="w3-btn w3-blue w3-border w3-round"/>&nbsp;
				<%}%>
			<%}%>
			<%if(historyMax == 0){ %>
				<%if(emp_no.equals(apprDoc.getEmp_no())){ %>
			<input type="button" onclick="javascript:removeCheck(<%=apprDoc.getAppr_no() %>);" value="삭제" class="w3-btn w3-blue w3-border w3-round"/>&nbsp;
				<%}%>
			<%}%>
			<input type="button"  onclick="javascript:listCheck();" value="목록" class="w3-btn w3-blue w3-border w3-round " />&nbsp;
			<!-- <a href="./ApprDocList.appr">
				[목록]
			</a>&nbsp;&nbsp; -->
						
			</div>
			<br>
	</form>

<!-- 게시판 수정 -->
</div>
</body>
</html>
			
	
