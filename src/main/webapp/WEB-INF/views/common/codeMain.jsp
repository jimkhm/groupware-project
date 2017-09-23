<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="spring.mybatis.gw.common.dto.CodeMasterDTO"%>
<%
	List mlist = (List) request.getAttribute("masterList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<link rel="stylesheet"
	href="http://www.w3schools.com/lib/w3-theme-green.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script src="./resources/js/codeMgr.js?ver=1"></script>

</head>
<body>

<!-- 마스터 코드 등록 -->
	<div class="w3-panel w3-blue w3-card-8 w3-left-align">
		<button
			onclick="document.getElementById('id01').style.display='block'"
			class="w3-btn w3-padding w3-blue">마스터 코드 등록</button>

		<div id="id01" class="w3-modal">
			<div class="w3-modal-content">
				<div class="w3-container">
					<span
						onclick="document.getElementById('id01').style.display='none'"
						class="w3-closebtn">&times;</span>
					<div class="w3-card-4">
						<div class="w3-container w3-brown">
							<h2>공통 코드 등록</h2>
						</div>
						<form class="w3-container" action="javascript:insert_Mcode()" method="post">
							<p>
								<label class="w3-label w3-text-brown"><b>코드</b></label> 
								<input class="w3-input w3-border" id="cd_m" type="text">
							</p>
							<p>
								<label class="w3-label w3-text-brown"><b>코드 명</b></label> 
								<input class="w3-input w3-border" id="cd_nm" type="text">
							</p>
							<p>
								<label class="w3-label w3-text-brown"><b>코드 설명</b></label> 
								<input class="w3-input w3-border" id="remark" type="text">
							</p>
							<p>
								<button class="w3-btn w3-brown">Register</button>
							</p>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- 마스터 코드 리스트  -->
<div id="mList">
	<div class="w3-row">
		<div class="w3-threequarter w3-btn w3-white w3-large w3-wide w3-text-shadow">
			<span class="w3-third" >코드</span> 
			<span class="w3-third" >코드명</span>
			<span class="w3-third" >설명</span>
		</div>	
	</div>
	<%
		if (mlist.size() > 0) {
			for (int i = 0; i < mlist.size(); i++) {
				CodeMasterDTO list = (CodeMasterDTO) mlist.get(i);
				String cd_m = list.getCd_m();
				String cd_nm = list.getCd_nm();
				String remark = list.getRemark();
	%>
	<div class="w3-accordion w3-border-bottom">
		<button id="btn_M_<%=cd_m%>" onclick="detailView('<%=cd_m%>')" class="w3-threequarter w3-btn w3-white w3-hover-blue ">
			<span class="w3-third" id="m_m_<%=cd_m%>"><%=cd_m%></span> 
			<span class="w3-third"  id="m_nm_<%=cd_nm%>"><%=cd_nm%></span>
			<span class="w3-third"  id="m_re_<%=remark%>"><%=remark%></span>
		</button>
		<button id="btn_M_U_<%=cd_m%>" class="w3-btn fa fa-pencil w3-xlarge w3-white " style="width:10%" onclick="update_Mcode('<%=cd_m%>', '<%=cd_nm%>', '<%=remark%>')"></button>
		<button id="btn_M_D_<%=cd_m%>" class="w3-btn fa fa-trash w3-xlarge w3-white " style="width:10%" onclick="delete_Mcode('<%=cd_m%>')"></button>
		<div id="m_List_<%=cd_m%>" class="w3-accordion-content w3-container"></div>
	</div>
	<%	}	%>
	<%}	%>
</div>
	
<!-- 마스터 코드 수정 -->
		<div id="updateM" class="w3-modal">
			<div class="w3-modal-content">
				<div class="w3-container">
					<span
						onclick="document.getElementById('updateM').style.display='none'"
						class="w3-closebtn">&times;</span>
					<div class="w3-card-4">
						<div class="w3-container w3-brown">
							<h2>공통 코드 수정</h2>
						</div>
						<form class="w3-container" action="javascript:updateM()" method="post">
							<p>
								<label class="w3-label w3-text-brown"><b>코드</b></label> 
								<input type="hidden" id="originCd_m" value="null"/>
								<input class="w3-input w3-border" id="updateCd_m" type="text">
							</p>
							<p>
								<label class="w3-label w3-text-brown"><b>코드 명</b></label>
								<input type="hidden" id="originCd_nm" value="null"/> 
								<input class="w3-input w3-border" id="updateCd_nm" type="text">
							</p>
							<p>
								<label class="w3-label w3-text-brown"><b>코드 설명</b></label>
								<input type="hidden" id="originRemark" value="null"/> 
								<input class="w3-input w3-border" id="updateRemark" type="text">
							</p>
							<p>
								<button class="w3-btn w3-brown">Update</button>
							</p>
						</form>
					</div>
				</div>
			</div>
		</div>	

</body>
</html>

<!-- 
	마스터 코드 리스트
	getComp_cd
	getCd_m
	getCd_nm
	getRemark
	getInsert_id
	getInsert_dt
	getUpdate_id
	getUpdate_dt
  -->