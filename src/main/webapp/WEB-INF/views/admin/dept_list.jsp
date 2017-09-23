<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
	String sComp_cd = (String) request.getSession().getAttribute("company"); // session company
	//String sComp_cd = "C001";
	//String sId = (String) request.getSession().getAttribute("id"); // session id 
	String company = sComp_cd;
//	if(request.getParameter("company") != null) {
//		company = request.getParameter("company");
//	}
	
	List list = (List) request.getAttribute("list");
	int listCount = list.size();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 정보</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
function viewIDCheckBtn(idx){
		
	var rtn = true;
	var count = parseInt(document.getElementById("rowCount").value, 10);
	
	// 앞 뒤 공백 제거
	document.forms["listForm"]["deptNo"+idx].value = document.forms["listForm"]["deptNo"+idx].value.trim();
	var idxRow = document.forms["listForm"]["deptNo"+idx].value; // *부서 코드
    	
   	for(var j=0; j < count; j++){
   		
   		if(idx != j){
   			document.forms["listForm"]["deptNo"+j].value = document.forms["listForm"]["deptNo"+j].value.trim();
   	    	var jRow = document.forms["listForm"]["deptNo"+j].value; // *부서 코드
   	    	   	    	
   	    	if(idxRow == jRow){ // 다른 row 에 같은 값이 존재한다!   	    		
   	    		rtn = false;
   	    	}
   		}
   		
   		if (rtn == false) {
   			document.getElementById("w_deptNo"+idx).innerHTML = "동일한 부서 코드가 이미 존재 합니다.<br />다른 부서 코드를 입력해 주세요.<br />";
   		} else {
   			document.getElementById("w_deptNo"+idx).innerHTML = "";
   		}
   			 
   	}

    return rtn;	
}
function search(idx){
	document.getElementById("idx").value = idx;
	var url = "./EmployeePop.admin?company=<%= company %>";
    window.open(url,"CLIENT_WINDOW","width=700, height=500, toolbar=no, menubar=no, location=no, scrollbars=yes, status=no,resizable=no, fullscreen=no, channelmode=no, directories=no;");
	return false;
}
function inRowLast(){
	var Tbl = document.all['tb1'];
	var tRow = Tbl.insertRow();  //그냥 맨 아래에 추가하므로, 별다른 변수없습니다.
	var count = parseInt(document.getElementById("rowCount").value, 10) + 1;
	var i = count - 1;
	var str ="";
	str = "<td class='w3-center' >&nbsp;</td>";
	tRow.insertCell(0).innerHTML = str;
	str = "<td class='w3-center' ><input name='mode" + i + "' id='mode" + i + "'  type='hidden' value='insert' />";
	str += "<center>";
	str += "<span id='w_deptNo" + i + "' class='w3-text-red'></span>";
	str += "<input name='mDeptNo" + i + "' type='hidden' value='M' />";
	str += "<input name='deptNo" + i + "' id='deptNo" + i + "'  type='text' value='' style='width:100px' ";
	str += " onkeyup=\"return checkMaxLength('listForm','deptNo" + i + "','w_deptNo" + i + "',4);\" ";
	str += " onchange=\"return viewIDCheckBtn(" + i + ");return checkMaxLength('listForm','deptNo" + i + "','w_deptNo" + i + "',4);\" ";
	str += " /></center></td> ";
	tRow.insertCell(1).innerHTML = str;
	str = "<td class='w3-center' ><center>";
	str += "<span id='w_deptNm" + i + "' class='w3-text-red'></span>";
	str += "<input name='deptNm" + i + "' id='deptNm" + i + "'  type='text' value='' style='width:200px' ";
	str += " onkeyup=\"return checkMaxLength('listForm','deptNm" + i + "','w_deptNm" + i + "',50);\" ";
	str += " onchange=\"return checkMaxLength('listForm','deptNm" + i + "','w_deptNm" + i + "',50);\" ";	
	str += " /></center></td> ";
	tRow.insertCell(2).innerHTML = str;
	var str ="";
	str += "<td class='w3-center'><center><input name='deptManager" + i + "' id='deptManager" + i + "' type='hidden' value='' style='display: inline-block;' />";
	str += "	<input name='deptManagerNm" + i + "' id='deptManagerNm" + i + "'  type='text' value='' style='width:150px;display: inline-block;' disabled />";
	str += "	<button type='button' class='w3-btn w3-blue w3-border w3-border-blank w3-round '";
	str += "	id='searchButton' onclick='return search(" + i + ");' style='display: inline-block;'>검색</button>";
	str += "</center></td>";

	tRow.insertCell(3).innerHTML = str;
	document.getElementById("rowCount").value = count;
	
	return false;
}
//UTF-8 관련 정보는 http://ko.wikipedia.org/wiki/UTF-8 를 참조해서 계산했다.
//UTF-8 문자열의 Byte 크기 구하기.
//주의!! : UTF-8 웹 페이지에서만 정상작동한다. EUC-KR에서는 작동하지 않는다.
//참고 자료 : http://ko.wikipedia.org/wiki/UTF-8 위키 백과의 UTF-8 항목
//테스트 환경 : IE6, IE7, FF 2.0, Safari 3, Opera 9.2
//각 문자의 유니코드 코드를 분석하여, UTF-8로 변환시 차지하는
//byte 수를 리턴한다.
function charByteSize(ch) {
	if (ch == null || ch.length == 0) {
		return 0;
	}
	
	var charCode = ch.charCodeAt(0);
	
	if (charCode <= 0x00007F) {
		return 1;
	} else if (charCode <= 0x0007FF) {
		return 2;
	} else if (charCode <= 0x00FFFF) {
		return 3;
	} else {
		return 4;
	}
}
//문자열을 UTF-8로 변환했을 경우 차지하게 되는 byte 수를 리턴한다.
function stringByteSize(str) {
	if (str == null || str.length == 0) {
		return 0;
	}
	var size = 0;
	
	for (var i = 0; i < str.length; i++) {
		size += charByteSize(str.charAt(i));
	}
	return size;
}
//maxlength 체크
function checkMaxLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len > size) {
		document.getElementById(spanId).innerHTML = size + "byte 초과입니다.";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
//minlength 체크
function checkMinLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < size) {
		document.getElementById(spanId).innerHTML = size + "byte 미만입니다.";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}	
}
//max&min length 체크
function checkLength(formName, inputName, spanId, sizeMin, sizeMax){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < sizeMin) {
		document.getElementById(spanId).innerHTML = sizeMin + "byte 미만입니다.";
	} else if(len > sizeMax) {
		document.getElementById(spanId).innerHTML = sizeMax + "byte 초과입니다.";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
function validateForm() {
	
	var count = parseInt(document.getElementById("rowCount").value, 10);
	var rtn = true;
	
	// 앞 뒤 공백 제거
	for(var i=0; i < count; i++){
		
    	document.forms["listForm"]["deptNo"+i].value = document.forms["listForm"]["deptNo"+i].value.trim();
    	document.forms["listForm"]["deptNm"+i].value = document.forms["listForm"]["deptNm"+i].value.trim();
    	
    	var check1 = document.forms["listForm"]["deptNo"+i].value; // *부서 코드
        var check2 = document.forms["listForm"]["deptNm"+i].value; // *부서명
	
        if (check1 == null || check1 == "") {
        	alert("부서 코드를 입력해 주세요.");
            document.forms[formName]["deptNo"+i].focus();
            rtn = false;
            break;
        }else if (check2 == null || check2 == "") {
        	alert("부서명을 입력해 주세요.");
        	document.forms[formName]["deptNm"+i].focus();
        	rtn = false;
        	break;
        }
        
	}
	
	return rtn;
}
function dataSave(){
	
	var form = document.getElementById("listForm");

	if(true==validateForm()){
		form.action = "./DeptSave.admin";
		form.submit();
		return true;
	}
	return false;
}
</script>
</head>
<body class="w3-container">
    
<div class="w3-container">
<h2>부서 정보</h2>

	<!-- 부서 정보 리스트 -->
	<div id="aaa" class="w3-col w3-container" style="width:850px">	
	<form name="listForm" id="listForm" method="post">
	<input type="hidden" name="idx" id="idx" value="" />
	<input type="hidden" name="rowCount" id="rowCount" value="<%= listCount %>" />
	<input type="hidden" name="comp_cd" value="<%= company %>" />
	
<table id="tb1" class="w3-table w3-striped w3-bordered w3-card-4" id="myTable">
<thead>
<tr class="w3-blue">
<th class="w3-center">삭제 여부</th>
<th class="w3-center">*부서 코드</th>
<th class="w3-center">*부서명</th>
<th class="w3-center">부서장</th>
</tr>
</thead>
<%
	String	comp_cd;			// 회사 코드
	String	dept_no;			// 부서 코드
	String	dept_nm;			// 부서명
	String	dept_manager;		// 부서장(사번)
	String	dept_manager_nm;	// 부서장(이름)	
	String	m_dept_no;			// 상위 부서 코드
	
	if(listCount == 0) { %>
<tr>
<td  colspan="3" class="w3-center">조회된 자료가 없습니다.</td>
</tr>
<%
	} else {
	
	for(int i = 0; i < listCount ; i++) {
		spring.mybatis.gw.admin.dto.DeptDTO data = (spring.mybatis.gw.admin.dto.DeptDTO)list.get(i);
		comp_cd = data.getComp_cd();
		if (comp_cd==null) {comp_cd = "";}
		dept_no = data.getDept_no();
		if (dept_no==null) {dept_no = "";}
		dept_nm = data.getDept_nm();
		if (dept_nm==null) {dept_nm = "";}
		dept_manager = data.getDept_manager();
		if (dept_manager==null) {dept_manager = "";}
		dept_manager_nm = data.getDept_manager_nm();
		if (dept_manager_nm==null) {dept_manager_nm = "";}
		m_dept_no = data.getM_dept_no();
		if(m_dept_no==null || m_dept_no.equals("")) { m_dept_no = "M"; }
		
%>
<tr>
<td class="w3-center"><input name="checkYn<%= i %>" id="chk<%= i %>" class="w3-check" type="checkbox" value="Y"></td>
<td class="w3-center"><input name="mode<%= i %>" id="mode<%= i %>"  type="hidden" value="update" />
<span id="w_deptNo<%= i %>" class="w3-text-red"></span>
<input name="mDeptNo<%= i %>" type="hidden" value="<%= m_dept_no %>" />
<input name="deptNo<%= i %>" id="deptNo<%= i %>"  type="text" value="<%= dept_no %>" style="width:100px"
onkeyup="return checkMaxLength('listForm','deptNo<%= i %>','w_deptNo<%= i %>',4);"
onchange="return viewIDCheckBtn(<%= i %>);return checkMaxLength('listForm','deptNo<%= i %>','w_deptNo<%= i %>',4);" />
</td>
<td class="w3-center">
<span id="w_deptNm<%= i %>" class="w3-text-red"></span>
<input name="deptNm<%= i %>" id="deptNm<%= i %>"  type="text" value="<%= dept_nm %>" style="width:200px"
onkeyup="return checkMaxLength('listForm','deptNm<%= i %>','w_deptNm<%= i %>',50);"
onchange="return checkMaxLength('listForm','deptNm<%= i %>','w_deptNm<%= i %>',50);" /></td>
<td class="w3-center"><input name="deptManager<%= i %>" id="deptManager<%= i %>"  type="hidden" value="<%= dept_manager %>" style="display: inline-block;" />
<input name="deptManagerNm<%= i %>" id="deptManagerNm<%= i %>"  type="text" value="<%= dept_manager_nm %>" style="width:150px;display: inline-block;" disabled />
	<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round "
	id="searchButton" onclick="return search(<%= i %>);" style="display: inline-block;">검색</button>
</td>
</tr>
<% } } %>
</table>
<br />
	<p class="w3-center">
	
<button type="button"  class="w3-btn w3-blue w3-border w3-border-blank w3-round " 
	id="addRowButton" onclick="return inRowLast();" >추가</button>
	&nbsp;

<button type="button"  class="w3-btn w3-blue w3-border w3-border-blank w3-round " 
	id="saveButton" onclick="return dataSave();" >저장</button>
	</p>
	</form>
</div>
</div>

</body>
</html>