<%--
 //******************************************************************************
//  프로그램ID : employee_list.jsp
//  프로그램명 : 사원 정보
//  관련 DB 테이블 : EMPLOYEE_INFO
//  기타 DB 테이블 : DEPT_INFO, CODE_MASTER, CODE_DETAIL, COMPANY_INFO
//  작  성  자 : 조 수 정 (Sujung Jo)
//  작  성  일 : 2016.09.29.
//--------------------------------------
//  수  정  자 : 조 수 정 (Sujung Jo)
//  수  정  일 : 2016.11.14.
//  변경 내용 : javascript validateForm() 패스워드 체크 로직 변경
//                  - 신규로 등록할 때는 null 이면 체크함. 기존 데이터가 변경되면(null 이 아니면) 그냥 update 이기 때문에 pass
//                  javascript selRow() 패스워드 value 값 넣어주는 부분 변경 default value 값 없음 (DB에서 읽어오지 않음)
//******************************************************************************
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%
	String sComp_cd = (String) request.getSession().getAttribute("company"); // session company
	//String sComp_cd = "C001";
	//String sId = (String) request.getSession().getAttribute("id"); // session id 
	String company = sComp_cd;
//	if(request.getParameter("company") != null) {
//		company = request.getParameter("company");
//	}
	
	String defaultRow = "";
	
	String searchGubun = "";
	String searchInput = "";
	
	List list = (List) request.getAttribute("list");
	int listCount = list.size();
	List deptList = (List) request.getAttribute("selDeptList");
	int deptListCount = deptList.size();
	List roleList = (List) request.getAttribute("selRoleList");
	int roleListCount = roleList.size();
	
	defaultRow = (String) request.getAttribute("defaultRow");
	
	searchGubun = (String) request.getAttribute("searchGubun");
	searchInput = (String) request.getAttribute("searchInput");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 정보</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
function viewIDCheckBtn(formName){
	var btn = document.getElementById("idCheckButton");
	var inputVal = document.forms[formName]["emp_id"].value;
	var defaultVal = document.forms[formName]["emp_id"].placeholder;
	if(inputVal != defaultVal){
		btn.className = btn.className.replace(" w3-blue", " w3-pink");
	} else {
		btn.className = btn.className.replace(" w3-pink", " w3-blue");
	}
    return;	
}
function viewEmpCheckBtn(formName){
	var btn = document.getElementById("empCheckButton");
	var inputVal = document.forms[formName]["emp_no"].value;
	var defaultVal = document.forms[formName]["emp_no"].placeholder;
	if(inputVal != defaultVal){
		btn.className = btn.className.replace(" w3-blue", " w3-pink");
	} else {
		btn.className = btn.className.replace(" w3-pink", " w3-blue");
	}
    return;	
}
// ID 체크
function checkID(formName){//true/false = isEmpId(comp,id)
	var btn = document.getElementById("idCheckButton");
    var serData = $('#'+formName).serialize(); // 폼에 입력된 여러가지 값을 구분하여 가져옴
    if (btn.className.indexOf("w3-blue") == -1) {
	    $.post(
	    	"./EmpIDCheck.admin",
	    	serData,
	    	function(data) {
	    		var jsonData = JSON.parse(data);
	    		if(jsonData.result){
	    			alert("해당 아이디를 등록하시면 됩니다.");
	    			btn.className = btn.className.replace(" w3-pink", " w3-blue");
	    		} else {
	    			alert("동일한 아이디가 이미 등록되어 있습니다.");
	    			btn.className = btn.className.replace(" w3-blue", " w3-pink");
	    		}
	    	}
	    );
    }
}
// EMP 체크
function checkEmp(formName){//true/false = isEmpNo(comp,emp)
	var btn = document.getElementById("empCheckButton");
    var serData = $('#'+formName).serialize(); // 폼에 입력된 여러가지 값을 구분하여 가져옴
    if (btn.className.indexOf("w3-blue") == -1) {
	    $.post(
	    	"./EmpNoCheck.admin",
	    	serData,
	    	function(data) {
	    		var jsonData = JSON.parse(data);
	    		if(jsonData.result){
	    			alert("해당 사번을 등록하시면 됩니다.");
	    			btn.className = btn.className.replace(" w3-pink", " w3-blue");
	    		} else {
	    			alert("동일한 사번이 이미 등록되어 있습니다.");
	    			btn.className = btn.className.replace(" w3-blue", " w3-pink");
	    		}
	    	}
	    );
    }
}
	$(function(){
		$('input[type=text]').prop("disabled", true);
		$('select').prop("disabled", true);
		
		/* 체크 박스 전체 선택/해제 */
		$('#checkall').click(function(){			
			var chk = $(this).prop('checked');			
			$('input[type=checkbox]').prop("checked", chk);
		});
		
		$('#edit').click(function(){			
			$('input[type=text]').prop("disabled", false);
			$('input[type=password]').prop("disabled", false);
			$('select').prop("disabled", false);
		});
		
		$('#searchGubun').prop("disabled", false);
		$('#searchInput').prop("disabled", false);
		
		$('#searchInput').keypress(function(event){
			if(event.keyCode == 13){
				$('#searchButton').click();
			}
		});
				
	});

function getOption(name) { // 버튼을 클릭하면 선택되어진 값을 가지고 올 수 있다.
    var obj = document.getElementById("selected" + name);
    document.getElementById(name+"List").innerHTML = obj.options[obj.selectedIndex].value; 
    /*obj.options[obj.selectedIndex].text;*/
}
function selRow(idx) {
	//var obj = document.getElementById("chk" + idx);
	var objEmp = document.getElementById("empNo" + idx);
	document.getElementById("selectedEmp").value = objEmp.value;
	document.getElementById("selectedEmp").placeholder = objEmp.value;
	document.getElementById("selectedEmp").disabled = true;
	
	var objId = document.getElementById("empId" + idx);
	document.getElementById("selectedId").value = objId.value;
	document.getElementById("selectedId").placeholder = objId.value;
	document.getElementById("selectedId").disabled = true;
	
	//var objPw = document.getElementById("empPw" + idx);
	//document.getElementById("selectedPw").value = objPw.value;
	// 패스워드는 변경될 경우에 저장을 한다. DB에서 읽어오는 값을 default로 뿌려주는게 아님!!
	document.getElementById("selectedPw").value = "";
	document.getElementById("selectedPw").disabled = true;
	
	var objNm = document.getElementById("empNm" + idx);
	document.getElementById("selectedName").value = objNm.value;
	document.getElementById("selectedName").placeholder = objNm.value;
	document.getElementById("selectedName").disabled = true;
	
	var objDeptNo = document.getElementById("deptNo" + idx);
	document.getElementById("selectedDept").value = objDeptNo.value;
	document.getElementById("selectedDept").disabled = true;
	
	var objRoleNo = document.getElementById("roleCd" + idx);
	document.getElementById("selectedRole").value = objRoleNo.value;
	document.getElementById("selectedRole").disabled = true;

	var objPhone = document.getElementById("empPhone" + idx);
	document.getElementById("selectedPhone").value = objPhone.value;
	document.getElementById("selectedPhone").placeholder = objPhone.value;
	document.getElementById("selectedPhone").disabled = true;
	
	var objEmail = document.getElementById("empEmail" + idx);
	document.getElementById("selectedEmail").value = objEmail.value;
	document.getElementById("selectedEmail").placeholder = objEmail.value;
	document.getElementById("selectedEmail").disabled = true;
	
	var objAddr = document.getElementById("empAddr" + idx);
	document.getElementById("selectedAddr").value = objAddr.value;
	document.getElementById("selectedAddr").placeholder = objAddr.value;
	document.getElementById("selectedAddr").disabled = true;
	
	var objMode = document.getElementById("mode");
	objMode.value = "update";
	
	document.forms["listForm"]["defaultRow"].value = idx;
	document.getElementById("selectedRow").value = idx;
}
function dataDelete(formName) {
	<%= listCount > 0 ? " " : "return false;" %>

	if(formName == "listForm"){
		// checkbox에 선택된 데이터가 있는지?
		var sum = 0;
		for (var i = 0; i < <%= listCount %>; i++) {
			if(document.getElementById("chk" + i).checked){
				sum++;
			}
		}

	} else {
		document.getElementById("checkall").checked = false;
		var selectedEmp = document.getElementById("selectedEmp").value;
		for (var i = 0; i < <%= listCount %>; i++) {
			var objEmp = document.getElementById("empNo" + i).value;
			if(objEmp==selectedEmp){ // 현재 조회된 row
				document.getElementById("chk" + i).checked = true;
			} else {
				document.getElementById("chk" + i).checked = false;	
			}
		}
	}

	var form = document.getElementById('listForm');
	if (sum > 0 && confirm("기존 관련 데이터가 있을 수 있습니다 정말로 삭제 하시겠습니까?")) {
		form.action = "./EmployeeDelete.admin";
		form.submit();
		return true;
	}else if(sum==0){
		//alert("선택된 데이터가 없습니다."); // error!! 검색어 text에서 엔터키를 치면 이 메시지가 계속 뜬다		
	}
	return false;
}
function dataEdit(formName) {
	//input[type="text"]:disabled {
	//<input id="selectedEmp" class="w3-input" type="text" value="" disabled/>
	//var obj = document.getElementById("selectedId");
	var obj = document.getElementById("selectedEmp");
	//alert(obj.disabled);
	return;
}
function dataUndo(formName) {
	var form = document.getElementById(formName);
	form.reset();
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
function validateForm(formName) {
	
	var objMode = document.getElementById("mode"); // 신규인지 수정인지 확인을 위해...
	
	var reg1 = /[0-9]{0,2}-?[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/; // 전화번호
	var reg2 = /^((\w|[\-\.])+)@((\w|[\-\.][^(\.)\1])+)\.([A-Za-z]+)$/; // 이메일
	
	// 앞 뒤 공백 제거
    document.forms[formName]["emp_no"].value = document.forms[formName]["emp_no"].value.trim();
    document.forms[formName]["emp_id"].value = document.forms[formName]["emp_id"].value.trim();
    document.forms[formName]["emp_pw"].value = document.forms[formName]["emp_pw"].value.trim();
    document.forms[formName]["emp_nm"].value = document.forms[formName]["emp_nm"].value.trim();
    document.forms[formName]["emp_phone"].value = document.forms[formName]["emp_phone"].value.trim();
    document.forms[formName]["emp_email"].value = document.forms[formName]["emp_email"].value.trim();
    document.forms[formName]["emp_addr"].value = document.forms[formName]["emp_addr"].value.trim();
	
    var check1 = document.forms[formName]["emp_no"].value; // *사번
    var check2 = document.forms[formName]["emp_id"].value; // *아이디
    var check3 = document.forms[formName]["emp_pw"].value; // *비밀번호
    var check4 = document.forms[formName]["emp_nm"].value; // *이름
    var check5 = document.forms[formName]["emp_phone"].value; // 전화번호
    var check6 = document.forms[formName]["emp_email"].value; // 이메일
    var check7 = document.forms[formName]["emp_addr"].value; // 주소
    
    if (check1 == null || check1 == "") {
    	alert("사번을 입력해 주세요.");
        document.forms[formName]["emp_no"].focus();
        return false;
    }else if (check2 == null || check2 == "") {
    	alert("아이디를 입력해 주세요.");
    	document.forms[formName]["emp_id"].focus();
    	return false;
    }else if (objMode.value == "new" && (check3 == null || check3 == "")) { // 신규로 등록할 때는 null 이면 체크함. 기존 데이터가 변경되면(null 이 아니면) 그냥 update 이기 때문에 pass
    	alert("비밀번호를 입력해 주세요.");
    	document.forms[formName]["emp_pw"].focus();
    	return false;
    }else if (check4 == null || check4 == "") {
    	alert("이름을 입력해 주세요.");
    	document.forms[formName]["emp_nm"].focus();
    	return false;
    }else if (check5 != null && check5 != "" && check5.search(reg1) == -1) {
    	alert("전화 번호는 숫자와 - 로 입력해 주세요.");
    	document.forms[formName]["emp_phone"].focus();
    	return false;
    }else if (check6 != null && check6 != "" && check6.search(reg2) == -1) {
    	alert("이메일은 <영문자 숫자 @ . - _> 로 입력해 주세요.");
    	document.forms[formName]["emp_email"].focus();
    	return false;
    }else {
    	return true;
    }
    
}
function dataSave(formName){
	var btn1 = document.getElementById("empCheckButton");
    var btn2 = document.getElementById("idCheckButton");
    var form = document.getElementById(formName);
	var mode = document.getElementById("mode").value;
	
	if (btn1.className.indexOf("w3-blue") == -1) {
		alert("유효한 사번인지 사번 체크 버튼을 클릭하여 체크해 주세요.");
		return false;
	}
	if (btn2.className.indexOf("w3-blue") == -1) {
		alert("유효한 아이디인지 아이디 체크 버튼을 클릭하여 체크해 주세요.");
		return false;
	}
	if(true==validateForm(formName)){
		if (mode == "new") {
			form.action = "./EmployeeNew.admin";
		} else {
			form.action = "./EmployeeSave.admin";
		}
		form.submit();
		return true;
	}
	return false;
}
function addNew(){
	var form = document.getElementById("detailFrom");
	document.getElementById("selectedEmp").placeholder = "";
	document.getElementById("selectedId").placeholder = "";
	document.getElementById("selectedPw").placeholder = "";
	document.getElementById("selectedName").placeholder = "";
	document.getElementById("selectedPhone").placeholder = "";
	document.getElementById("selectedEmail").placeholder = "";
	document.getElementById("selectedAddr").placeholder = "";
	form.reset();
	var objMode = document.getElementById("mode");
	objMode.value = "new";
	return false;
}
function search(){
	var form = document.getElementById("listForm");
	document.forms["listForm"]["searchInput"].value = document.forms["listForm"]["searchInput"].value.trim();
	var inputCheck = document.forms["listForm"]["searchInput"].value;
	var searchGubun = document.forms["listForm"]["searchGubun"].value;
    if (searchGubun != "ALL" && (inputCheck == null || inputCheck == "")) {
    	alert("검색어를 입력해주세요.");
    	document.forms["listForm"]["searchInput"].focus();
        return false;
	}
    
    form.action = "./EmployeeInfo.admin";
	form.submit();
	return true;
}
function setDefaultRow(){
	var form = document.getElementById("listForm");
	var defaultRow = document.forms["listForm"]["defaultRow"].value;
	
	selRow(defaultRow);
	return;
}
</script>
</head>
<body class="w3-container" <%= listCount > 0 ? "onload=\"setDefaultRow();document.getElementById('searchButton').focus();\"" : " " %> >

<div class="w3-container">
<h2>사원 정보</h2>

<div id="bbb" class="w3-row">
<div id="aaa" class="w3-col w3-container" style="width:650px">
<form name="listForm" id="listForm" method="post">
<input type="hidden" name="company" value="<%= company %>" />
<input type="hidden" name="defaultRow" value="<%= defaultRow == null ? "0" : defaultRow %>" />

<!-- 검색어>선택[사원명] >입력text>검색 -->
<span style="font-size:16px;valign:bottom;"><strong>검색 : </strong></span>
<select name="searchGubun" id="searchGubun" class="w3-input" style="width:150px;font-size:16px;display: inline-block;" >
	<option value="ALL" <%= searchGubun.equals("ALL") ?  "selected" : " " %>>전체</option>
	<option value="empName" <%= searchGubun.equals("empName") ?  "selected" : " " %>>이름</option>
	<option value="deptName" <%= searchGubun.equals("deptName") ?  "selected" : " " %>>부서</option>
	<option value="roleName" <%= searchGubun.equals("roleName") ?  "selected" : " " %>>직급</option>
</select>
<input name="searchInput" id="searchInput"  type="text" style="width:300px;font-size:16px;display: inline-block;"
	value="<%= (searchInput == null || searchInput.equals("")) ?  "" : searchInput %>" placeholder="검색어를 입력하세요." />
	<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round "
	id="searchButton" onclick="return search();">검색</button>
<br />
<div style="height:5px;"></div>

<!-- list -->
<input type="hidden" name="comp_cd" value="<%= company %>" />
<input type="hidden" name="listCount" value="<%= listCount %>" />
<table class="w3-table w3-striped w3-bordered w3-card-4" id="myTable">
<thead>
<tr class="w3-blue">
<th><input id="checkall" class="w3-check" type="checkbox"></th>
<th>사번</th>
<th>아이디</th>
<th>이름</th>
<th>부서</th>
<th>직급</th>
</tr>
</thead>
<%
	String	comp_cd;		//	회사 코드
	String	emp_no;		//	사번
	String	emp_id;		//	아이디
	String	emp_pw;		//	비밀번호
	String	emp_nm;		//	이름
	String	dept_no;		//	부서코드
	String	emp_role_cd;//	직급코드
	String	emp_phone;	//	전화번호
	String	emp_email;	//	이메일
	String	emp_addr;	//	주소
	String	dept_nm;		// 부서명**
	String	emp_role_nm;//직급명**
	String	check_yn;		//삭제여부**
	
	if(listCount == 0) { %>
<tr>
<td  colspan="6" class="w3-center">조회된 자료가 없습니다.</td>
</tr>
<%
	} else {
	
	for(int i = 0; i < listCount ; i++) {
		spring.mybatis.gw.admin.dto.EmployeeDTO data = (spring.mybatis.gw.admin.dto.EmployeeDTO)list.get(i);
		comp_cd = data.getComp_cd();
		if (comp_cd==null) {comp_cd = "";}		
		emp_no = data.getEmp_no();
		if (emp_no==null) {emp_no = "";}
		emp_id = data.getEmp_id();
		if (emp_id==null) {emp_id = "";}
		emp_pw = data.getEmp_pw();
		if (emp_pw==null) {emp_pw = "";}
		emp_nm = data.getEmp_nm();
		if (emp_nm==null) {emp_nm = "";}
		dept_no = data.getDept_no();
		if (dept_no==null) {dept_no = "";}
		emp_role_cd = data.getEmp_role_cd();
		if (emp_role_cd==null) {emp_role_cd = "";}
		dept_nm = data.getDept_nm();
		if (dept_nm==null) {dept_nm = "";}
		emp_role_nm = data.getEmp_role_nm();
		if (emp_role_nm==null) {emp_role_nm = "";}		
		emp_phone = data.getEmp_phone();
		if (emp_phone==null) {emp_phone = "";}		
		emp_email = data.getEmp_email();
		if (emp_email==null) {emp_email = "";}		
		emp_addr = data.getEmp_addr();
		if (emp_addr==null) {emp_addr = "";}		
		check_yn = data.getCheck_yn();
		if (check_yn==null) {check_yn = "";}
%>
<tr onclick="selRow(<%= i %>);">
<td><input name="check_yn<%= i %>" id="chk<%= i %>" class="w3-check" type="checkbox" value="Y">
<input id="empPw<%= i %>"  type="hidden" value="<%= emp_pw %>" />
<input id="empPhone<%= i %>"  type="hidden" value="<%= emp_phone %>" />
<input id="empEmail<%= i %>"  type="hidden" value="<%= emp_email %>" />
<input id="empAddr<%= i %>"  type="hidden" value="<%= emp_addr %>" />
</td>
<td><%= emp_no %><input name="emp_no<%= i %>" id="empNo<%= i %>"  type="hidden" value="<%= emp_no %>" /></td>
<td><%= emp_id %><input id="empId<%= i %>"  type="hidden" value="<%= emp_id %>" /></td>
<td><%= emp_nm %><input id="empNm<%= i %>"  type="hidden" value="<%= emp_nm %>" /></td>
<td><%= dept_nm %><input id="deptNo<%= i %>"  type="hidden" value="<%= dept_no %>" /></td>
<td><%= emp_role_nm %><input id="roleCd<%= i %>"  type="hidden" value="<%= emp_role_cd %>" />
</td>
</tr>
<% } } %>
</table>

<!-- button -->
<!--삭제(선택한것 삭제)/추가(신규)/(변경은 조회한 후 변경하기)-->
<br />
<div class="w3-row-padding">
	<button id="delButton" class="w3-btn w3-red w3-left" onclick="return dataDelete('listForm');">선택 삭제</button>
	<button id="addButton" class="w3-btn w3-blue w3-right" onclick="return addNew();">사원 추가</button>	
</div>

</form>
</div>
<!-- 요기서 부터 오른쪽 -->
<!-- detail -->
<div class="w3-rest w3-container w3-light-grey" style="height:700px;">
	<form name="detailFrom" id="detailFrom" method="post" class="w3-container">
	<input type="hidden" id="mode" value="new" />
	<input type="hidden" name="comp_cd" value="<%= company %>" /><!-- 회사 코드 input hidden -->
	<input type="hidden" id="selectedRow" name="defaultRow" value="" />
	
	<br />
	<div style="font-size:24px;">
	<span>사원 상세 정보</span>
	<span class="w3-right">
		<i class="fa fa-trash-o" style="color:red" onclick="return dataDelete('detailFrom');"></i>
		<i id="edit" class="fa fa-edit" style="color:blueviolet" onclick="return dataEdit('detailFrom');"></i>
		<!-- <i id="undo" class="fa fa-undo" style="color:blueviolet" onclick="return dataUndo('detailFrom');"></i> -->
		<i class="fa fa-save" style="color:darkblue" onclick="return dataSave('detailFrom');"></i>		
	</span>
	</div>
	<br />
	
	<div >
	<label class="w3-label">*사번<span id="w_emp_no" class="w3-text-red"></span></label><br />
	<input name="emp_no" id="selectedEmp" class="w3-threequarter w3-input" type="text" value=""  placeholder="" disabled
 	 onkeyup="return checkLength('detailFrom','emp_no','w_emp_no',4,20);"
	 onchange="viewEmpCheckBtn('detailFrom');return checkLength('detailFrom','emp_no','w_emp_no',4,20);" />&nbsp;
	<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small "
	id="empCheckButton" onclick="return checkEmp('detailFrom');">&nbsp;사번 체크&nbsp;</button>
	</div>
	<br/>
	
	<div >
	<label class="w3-label">*아이디<span id="w_emp_id" class="w3-text-red"></span></label><br />
	<input name="emp_id" id="selectedId" class="w3-threequarter w3-input" type="text" value="" placeholder=""
	 onkeyup="return checkLength('detailFrom','emp_id','w_emp_id',4,20);"
	 onchange="viewIDCheckBtn('detailFrom');return checkLength('detailFrom','emp_id','w_emp_id',4,20);" />&nbsp;
	<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small "
	id="idCheckButton" onclick="return checkID('detailFrom');">아이디 체크</button>
	</div>
	
	<div ><br />
	<label class="w3-label">*비밀번호<span id="w_emp_pw" class="w3-text-red"></span></label><br />
	<input name="emp_pw" id="selectedPw" class="w3-threequarter w3-input" type="password"  placeholder="" value=""
	 onkeyup="return checkLength('detailFrom','emp_pw','w_emp_pw',4,20);"
	 onchange="return checkLength('detailFrom','emp_pw','w_emp_pw',4,20);" />
	</div>
	<br/>
	
	<div ><br />
	<label class="w3-label">*이름<span id="w_emp_nm" class="w3-text-red"></span></label><br />
	<input name="emp_nm" id="selectedName" class="w3-threequarter w3-input" type="text"  placeholder="" value=""
	 onkeyup="return checkMaxLength('detailFrom','emp_nm','w_emp_nm',100);"
	 onchange="return checkMaxLength('detailFrom','emp_nm','w_emp_nm',100);" />
	</div>
	<br/>
	
	<div><br />
	<label class="w3-label">부서</label><br/>
	<select name="dept_no" id="selectedDept" class="w3-input" style="width:150px;font-size:16px;display: inline-block;" >
<%
	String cd;
	String nm;

	for(int i = 0; i < deptListCount ; i++) {
		spring.mybatis.gw.admin.dto.SelDeptDTO data = (spring.mybatis.gw.admin.dto.SelDeptDTO)deptList.get(i);
		cd = data.getCd();
		if (cd==null) {cd = "";}
		nm = data.getNm();
		if (nm==null) {nm = "";}	
		%>
  <option value="<%= cd %>" <%= i==0 ?  "selected" : " " %> ><%= nm %></option>
<% } %>
</select>

<br/>
	
	<label class="w3-label">직급</label><br/>
<select name="emp_role_cd" id="selectedRole" class="w3-input" style="width:150px;font-size:16px;display: inline-block;" >
<%
	String roleCd;
	String roleNm;

	for(int i = 0; i < roleListCount ; i++) {
		spring.mybatis.gw.admin.dto.SelRoleDTO data = (spring.mybatis.gw.admin.dto.SelRoleDTO)roleList.get(i);
		roleCd = data.getCd();
		if (roleCd==null) {roleCd = "";}
		roleNm = data.getNm();
		if (roleNm==null) {roleNm = "";}	
		%>
  <option value="<%= roleCd %>" <%= i==0 ?  "selected" : " " %> ><%= roleNm %></option>
<% } %>
</select>

	</div>
	
	<label class="w3-label">전화번호<span id="w_emp_phone" class="w3-text-red"></span></label><br />
	<input name="emp_phone" id="selectedPhone" class="w3-input" type="text" value=""  placeholder=""
	 onkeyup="return checkMaxLength('detailFrom','emp_phone','w_emp_phone',20);"
	 onchange="return checkMaxLength('detailFrom','emp_phone','w_emp_phone',20);" />
	
	<label class="w3-label">이메일<span id="w_emp_email" class="w3-text-red"></span></label><br />
	<input name="emp_email" id="selectedEmail" class="w3-input" type="text" value=""  placeholder=""
	 onkeyup="return checkMaxLength('detailFrom','emp_email','w_emp_email',100);"
	 onchange="return checkMaxLength('detailFrom','emp_email','w_emp_email',100);" />
	
	<label class="w3-label">주소<span id="w_emp_addr" class="w3-text-red"></span></label><br />
	<input name="emp_addr" id="selectedAddr" class="w3-input" type="text" value=""  placeholder=""
	 onkeyup="return checkMaxLength('detailFrom','emp_addr','w_emp_addr',200);"
	 onchange="return checkMaxLength('detailFrom','emp_addr','w_emp_addr',200);" />
	
	</form>
</div>
<!-- 요기서 까지 오른쪽 -->
</div>
</div>

</body>
</html>